// hex颜色转rgb颜色
function HexToRgb(str) {
  str = str.replace('#', '');
  const hxs = str.match(/../g);
  for (let i = 0; i < 3; i += 1) hxs[i] = parseInt(hxs[i], 16);
  return hxs;
}

/**
 * @return {string}
 */
function RgbToHex(a, b, c) {
  const hexs = [a.toString(16), b.toString(16), c.toString(16)];
  for (let i = 0; i < 3; i += 1) if (hexs[i].length === 1) hexs[i] = `0${hexs[i]}`;
  return `#${hexs.join('')}`;
}

// 得到hex颜色值为color的加深颜色值，level为加深的程度，限0-1之间
function getDarkColor(color, level) {
  const rgbc = HexToRgb(color);
  for (let i = 0; i < 3; i += 1) rgbc[i] = Math.floor(rgbc[i] * (1 - level));
  return RgbToHex(rgbc[0], rgbc[1], rgbc[2]);
}

// 得到hex颜色值为color的减淡颜色值，level为加深的程度，限0-1之间
function getLightColor(color, level) {
  const rgbc = HexToRgb(color);
  for (let i = 0; i < 3; i += 1) rgbc[i] = Math.floor((255 - rgbc[i]) * level + rgbc[i]);
  return RgbToHex(rgbc[0], rgbc[1], rgbc[2]);
}

export {
  getDarkColor,
  getLightColor,
};
