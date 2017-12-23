var SIGN_REGEXP = /([yMdhsm])(\1*)/g;
var DEFAULT_PATTERN = 'yyyy-MM-dd';

function padding(s, len) {
  var len = len - (s + '').length;
  for (var i = 0; i < len; i++) {
    s = '0' + s;
  }
  return s;
};

function getFileName() {
  return util.formatDate.format(new Date(), 'yyyyMMddhhmmss') + "_INSERT_com.worksap.company.hue.core.dto.TextDefDto.json";
}

function genjson(textId, lan, value) {
  let plkey = {
    textId: textId,
    locale: lan,
    plural: '',
  }
  let pldto = {
    key: {
      class: 'com.worksap.company.framework.textresource.TextResourceKey',
      pl: JSON.stringify(plkey)
    },
    value: value,
    systemFlg: false,
  }
  let template = {
    class: 'com.worksap.company.hue.core.dto.TextDefDto',
    pl: JSON.stringify(pldto)
  };
  return JSON.stringify(template);
}

export default {
  getFileName: function() {
    return this.formatDate.format(new Date(), 'yyyyMMddhhmmss') + "_INSERT_com.worksap.company.hue.core.dto.TextDefDto.json";
  },
  genText: function(list) {
    var str = '['
    list.forEach(item => {
      if (item.ja && item.ja != '') {
        str = str.concat(genjson(item.id, 'ja', item.ja)).concat(',');
      }
      if (item.en && item.en != '') {
        str = str.concat(genjson(item.id, 'en', item.en)).concat(',');
      }
    })
    str = str.slice(0, -1).concat(']');
    return str;
  },
  download: function(content, filename) {
    var eleLink = document.createElement('a');
    eleLink.download = filename;
    eleLink.style.display = 'none';
    // 字符内容转变成blob地址
    var blob = new Blob([content]);
    eleLink.href = URL.createObjectURL(blob);
    // 触发点击
    document.body.appendChild(eleLink);
    eleLink.click();
    // 然后移除
    document.body.removeChild(eleLink);
  },
  getQueryStringByName: function(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    var context = "";
    if (r != null)
      context = r[2];
    reg = null;
    r = null;
    return context == null || context == "" || context == "undefined" ? "" : context;
  },
  formatDate: {
    format: function(date, pattern) {
      pattern = pattern || DEFAULT_PATTERN;
      return pattern.replace(SIGN_REGEXP, function($0) {
        switch ($0.charAt(0)) {
          case 'y':
            return padding(date.getFullYear(), $0.length);
          case 'M':
            return padding(date.getMonth() + 1, $0.length);
          case 'd':
            return padding(date.getDate(), $0.length);
          case 'w':
            return date.getDay() + 1;
          case 'h':
            return padding(date.getHours(), $0.length);
          case 'm':
            return padding(date.getMinutes(), $0.length);
          case 's':
            return padding(date.getSeconds(), $0.length);
        }
      });
    },
    parse: function(dateString, pattern) {
      var matchs1 = pattern.match(SIGN_REGEXP);
      var matchs2 = dateString.match(/(\d)+/g);
      if (matchs1.length == matchs2.length) {
        var _date = new Date(1970, 0, 1);
        for (var i = 0; i < matchs1.length; i++) {
          var _int = parseInt(matchs2[i]);
          var sign = matchs1[i];
          switch (sign.charAt(0)) {
            case 'y':
              _date.setFullYear(_int);
              break;
            case 'M':
              _date.setMonth(_int - 1);
              break;
            case 'd':
              _date.setDate(_int);
              break;
            case 'h':
              _date.setHours(_int);
              break;
            case 'm':
              _date.setMinutes(_int);
              break;
            case 's':
              _date.setSeconds(_int);
              break;
          }
        }
        return _date;
      }
      return null;
    }

  }

};
