module.exports = {
  root: true,
  env: {
    node: true,
  },
  extends: [
    'plugin:vue/essential',
    '@vue/airbnb',
  ],
  parserOptions: {
    parser: 'babel-eslint',
  },
  rules: {
    'no-console': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
    'no-debugger': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
    'no-param-reassign': 'off',
    'max-len': 'off',
    'no-trailing-spaces': 'off',
    'import/no-unresolved': 'off',
    'import/extensions': 'off',
    'array-callback-return' : 'off',
    'func-names' : 'off',
    'linebreak-style' : "off",
  },
};
