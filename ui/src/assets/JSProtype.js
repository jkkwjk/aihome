// eslint-disable-next-line no-extend-native,func-names
Array.prototype.removeEqual = function (val) {
  const index = this.findIndex(val);
  if (index >= 0) {
    this.splice(index, 1);
  }
};
