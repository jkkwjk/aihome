/* eslint-disable no-unused-vars */
const path = require('path');

const resolve = (dir) => path.join(__dirname, dir);
const IS_PROD = ['production', 'prod'].includes(process.env.NODE_ENV);

const glob = require('glob');
const pagesInfo = require('./pages.config');

const pages = {};
glob.sync('./src/pages/**/main.js').forEach((entry) => {
  const chunk = entry.match(/\.\/src\/pages\/(.*)\/main\.js/)[1];
  const curr = pagesInfo[chunk];
  if (curr) {
    pages[chunk] = {
      entry,
      ...curr,
      chunk: ['chunk-vendors', 'chunk-common', chunk],
    };
  }
});

module.exports = {
  productionSourceMap: !IS_PROD, // 生产环境不生成 source map
  publicPath: IS_PROD ? `${process.env.VUE_APP_PUBLIC_PATH}` : `${process.env.VUE_APP_PUBLIC_PATH}`, // 默认'/'，部署应用包时的基本 URL
  pages,

  configureWebpack: (config) => {
    if (IS_PROD) {
      config.externals = {
        vue: 'Vue',
        'element-ui': 'ELEMENT',
        'vue-router': 'VueRouter',
        vuex: 'Vuex',
        axios: 'axios',
      };
    }
  },

  chainWebpack: (config) => {
    // 添加别名
    config.resolve.alias
      .set('vue$', 'vue/dist/vue.esm.js')
      .set('@', resolve('src'))
      .set('@assets', resolve('src/assets'))
      .set('@scss', resolve('src/assets/scss'))
      .set('@components', resolve('src/components'))
      .set('@plugins', resolve('src/plugins'))
      .set('@views', resolve('src/views'))
      .set('@router', resolve('src/router'))
      .set('@store', resolve('src/store'))
      .set('@layouts', resolve('src/layouts'))
      .set('@static', resolve('src/static'))
      .set('@util', resolve('src/util'))
      .set('@api', resolve('src/api'));

    if (IS_PROD) {
      // 使用cdn优化包大小
      const cdn = {
        // 访问https://unpkg.com/element-ui/lib/theme-chalk/index.css获取最新版本
        css: ['//unpkg.com/element-ui@2.10.1/lib/theme-chalk/index.css'],
        js: [
          '//unpkg.com/vue@2.6.10/dist/vue.min.js', // 访问https://unpkg.com/vue/dist/vue.min.js获取最新版本
          '//unpkg.com/vue-router@3.0.6/dist/vue-router.min.js',
          '//unpkg.com/vuex@3.1.1/dist/vuex.min.js',
          '//unpkg.com/axios@0.19.0/dist/axios.min.js',
          '//unpkg.com/element-ui@2.10.1/lib/index.js',
        ],
      };

      // 单页面cdn用这个
      // config.plugin("html").tap(args => {
      //   // html中添加cdn
      //   args[0].cdn = cdn;

      //   // 修复 Lazy loading routes Error
      //   args[0].chunksSortMode = "none";
      //   return args;
      // });

      // 防止多页面打包卡顿
      config.plugins.delete('named-chunks');

      // 如果使用多页面打包，使用vue inspect --plugins查看html是否在结果数组中
      // 多页面cdn添加
      Object.keys(pagesInfo).forEach((page) => {
        config.plugin(`html-${page}`).tap((args) => {
          // html中添加cdn
          args[0].cdn = cdn;

          // 修复 Lazy loading routes Error
          args[0].chunksSortMode = 'none';
          return args;
        });
      });
    }
  },

  css: {
    extract: IS_PROD,
    sourceMap: false,
    loaderOptions: {
      scss: {
        // 向全局sass样式传入共享的全局变量, $src可以配置图片cdn前缀
        // 详情: https://cli.vuejs.org/guide/css.html#passing-options-to-pre-processor-loaders
        prependData: `
        $src: "${process.env.VUE_APP_OSS_SRC}";
        `,

        // @import "@scss/variables.scss";
        // @import "@scss/mixins.scss";
        // @import "@scss/function.scss";

      },
    },
  },

  devServer: {
    port: 3030,
    proxy: {
      '/api': {
        target: 'http://localhost:8080', //          /api/a -> ..8080/a,
        ws: true,
        pathRewrite: {
          '^/api': '/',
          // pathRewrite: {'^/api': '/'} 重写之后url为 http://192.168.1.16:8085/xxxx
          // pathRewrite: {'^/api': '/api'} 重写之后url为 http://192.168.1.16:8085/api/xxxx
        },
      },
      '/': {
        target: 'http://localhost:8080',
        ws: true,
      },
    },
  },
};
