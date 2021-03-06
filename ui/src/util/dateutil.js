/* eslint-disable*/

/*
 *  Dateutil
 *  - provides formatting, parsing and other utility functions for dates.
 *
 * Copyright (c) 2009 Borgar ├×orsteinsson
 * Licensed under the terms of the MIT (LICENSE.txt) software license.
 *
 */
/* jslint laxbreak: true, undef: true *//* global module */
(function (__global__) {
  const SECOND_SIZE = 1000;
  const MINUTE_SIZE = SECOND_SIZE * 60;
  const HOUR_SIZE = MINUTE_SIZE * 60;
  const DAY_SIZE = HOUR_SIZE * 24;
  const WEEK_SIZE = DAY_SIZE * 7;
  const MONTH_SIZE = DAY_SIZE * 30.436875; // average month size
  const YEAR_SIZE = DAY_SIZE * 365.2425; // average year size

  const _toString = Object.prototype.toString;
  const _gl = __global__.lang = { en: {} };
  const _m = '01 02 03 04 05 06 07 08 09 10 11 12'.split(' ');
  const _d = 'Sunday Monday Tuesday Wednesday Thursday Friday Saturday'.split(' ');

  const method_size = {
    FullYear: 6,
    Month: 5,
    Date: 4,
    Hours: 3,
    Minutes: 2,
    Seconds: 1,
    Milliseconds: 0,
  };
  const method_map = {
    yr: 'FullYear',
    year: 'FullYear',
    years: 'FullYear',
    mn: 'Month',
    month: 'Month',
    months: 'Month',
    day: 'Date',
    days: 'Date',
    date: 'Date',
    hr: 'Hours',
    hour: 'Hours',
    hours: 'Hours',
    min: 'Minutes',
    minute: 'Minutes',
    minutes: 'Minutes',
    sec: 'Seconds',
    second: 'Seconds',
    seconds: 'Seconds',
    ms: 'Milliseconds',
    millisecond: 'Milliseconds',
    milliseconds: 'Milliseconds',
  };

  // *****************************************
  // *** *** *** formats & parsers *** *** ***
  // *****************************************

  var date_parsers = __global__._parsers = {

    // year + month + day + time
    // -- currently doesn't really support fractions on anything other than seconds
    // -- does not support timezones other than Zulu
    date_and_time: {
      test: /^(?:[+\-]\d{6}|\d{4})(?:(?:\-\d\d){1,2}|\d{4})[T ](?:\d\d)(?::?\d\d){0,2}(?:[\.,]\d+)?(?:Z|[+\-]\d\d(:?\d\d)?)?$/,
      size: 1,
      parse(str) {
        const b = str.split(/[T ]/);
        const date = date_parsers.date.parse(b[0]);
        const m = b[1].replace(/:/g, '')
          .match(/^(\d\d)(\d\d)?(\d\d)?(?:[.,](\d+))?([+\-](?:\d\d){1,2})?/);
        // if ( m[5] ) { var zone = m[5] || '0000'; }
        let fs = 0; let
          t = date.getTime()
                + parseInt(m[1], 10) * HOUR_SIZE
                + parseInt(m[2] || '0', 10) * MINUTE_SIZE
                + parseInt(m[3] || '0', 10) * SECOND_SIZE;
        if (m[3]) { fs = SECOND_SIZE; } else if (m[2]) { fs = MINUTE_SIZE; } else if (m[1]) { fs = HOUR_SIZE; }
        t += parseFloat(`0.${m[4] || '0'}`) * fs;
        date.setTime(t);
        date.size = 0;
        return date;
      },
    },

    // year + month + day
    date: {
      test: /^(?:[+\-]\d{6}|\d{4})(?:\-\d\d\-\d\d|\-?\d\d\d\d)$/,
      size: DAY_SIZE,
      parse(str) {
        const m = /^([+\-]\d{6}|\d{4})\-?(\d\d)\-?(\d\d)$/.exec(str);
        const d = __global__.date(m[1], +m[2] - 1, m[3]);
        d.size = DAY_SIZE;
        return d;
      },
    },

    // year + month
    year_and_month: {
      test: /^[+\-]?\d{4,6}[\/\-](?:0[1-9]|1[012])$/,
      size: MONTH_SIZE,
      parse(str) {
        const b = str.split(/[\/\-]/);
        const d = __global__.date(b[0], +b[1] - 1, 1);
        d.size = __global__.daysInMonth(d) * DAY_SIZE;
        return d;
      },
    },

    // year
    year: {
      test: /^[+\-]?\d{4,6}$/,
      size: YEAR_SIZE,
      parse(str) {
        const d = __global__.date(str, 0, 1);
        d.size = DAY_SIZE * (__global__.isLeapYear(d) ? 366 : 365);
        return d;
      },
    },

    // year + iso week + [day]
    year_and_week: {
      test: /^[+\-]?\d{4,6}\-?[Ww]\d\d(?:\-?\d)?$/,
      size: WEEK_SIZE,
      parse(str) {
        const s = str.toLowerCase().replace(/[^w\d]/g, '').split('w');
        const d = __global__.date(s[0], 0, 3); // Jan 3
        d.setUTCDate(3 - d.getUTCDay()
                      + (parseInt(s[1].substr(0, 2), 10) - 1) * 7
                      + parseInt(s[1].substr(2, 1) || '1', 10));
        d.size = WEEK_SIZE;
        return d;
      },
    },

    // year + day-of-year
    // -- we don't allow the short form yyyyddd because of ambiguity with yyyymmdd
    // -- 5 letter years would clash with cal-dates: yyyyyddd ~ yyyymmdd
    year_and_ordinal: {
      test: /^[+\-]?\d{4,6}\-[0-3]\d\d$/,
      size: DAY_SIZE,
      parse(str) {
        const d = new Date(0);
        d.setUTCFullYear(parseInt(str.substr(0, str.length - 4), 10));
        d.setDate(parseInt(str.substr(str.length - 3), 10));
        d.size = DAY_SIZE;
        return d;
      },
    },

    // year + quarter
    year_and_quarter: {
      test: /^[+\-]?\d{4,6}\-?[Qq][1-4]$/,
      size: YEAR_SIZE / 4,
      parse(str) {
        const b = str.split(/\-?[Qq]/);
        const d = __global__.date(b[0], (parseInt(b[1], 10) - 1) * 3);
        d.size = DAY_SIZE;
        return d;
      },
    },

  };

  const date_formatters = __global__._formats = {
    // Lowercase Ante meridiem and Post meridiem
    a(d) { return d.getUTCHours() >= 12 ? 'pm' : 'am'; },
    // Uppercase Ante meridiem and Post meridiem
    A(d) { return d.getUTCHours() >= 12 ? 'PM' : 'AM'; },
    // ISO 8601 date
    c(d, l) {
      return `${__global__.isoyear(d)
                               + __global__.format(d, '-m-d\\TH:i:s.', l)
                               + __global__.pad(d.getUTCMilliseconds(), 3)}Z`;
    },
    // Day of the month, 2 digits with leading zeros
    d(d) { return __global__.pad(d.getUTCDate()); },
    // A textual representation of a day, three letters
    D(d, l) { return __global__._(_d[d.getUTCDay()].substr(0, 3), l); },
    // Time zone identifier
    e(d) { return 'UTC'; },
    // A full textual representation of a month
    F(d, l) { return __global__._(_m[d.getUTCMonth()], l); },
    // 12-hour format of an hour without leading zeros
    g(d) { return d.getUTCHours() % 12 || 12; },
    // 24-hour format of an hour without leading zeros
    G(d) { return d.getUTCHours(); },
    // 12-hour format of an hour with leading zeros
    h(d) { return __global__.pad(d.getUTCHours() % 12 || 12); },
    // 24-hour format of an hour with leading zeros
    H(d) { return __global__.pad(d.getUTCHours()); },
    // Minutes with leading zeros
    i(d) { return __global__.pad(d.getUTCMinutes()); },
    // Day of the month without leading zeros
    j(d) { return d.getUTCDate(); },
    // A full textual representation of the day of the week
    l(d, l) { return __global__._(_d[d.getUTCDay()], l); },
    // Whether it's a leap year (0 = yes, 1 = no)
    L(d) { return __global__.isLeapYear(d) * 1; },
    // Numeric representation of a month, with leading zeros
    m(d) { return __global__.pad(d.getUTCMonth() + 1); },
    // A short textual representation of a month, three letters
    M(d, l) { return __global__._(_m[d.getUTCMonth()].substr(0, 3), l); },
    // Numeric representation of a month, without leading zeros
    n(d) { return d.getUTCMonth() + 1; },
    // ISO-8601 numeric representation of the day of the week
    N(d) { return d.getUTCDay() || 7; },
    // ISO-8601 year number
    o(d) { return __global__.pad(__global__.isocalendar(d)[0], 4); },
    // Time zone designator
    O(d) { return '+0000'; },
    // Time zone difference
    P(d) { return '+00:00'; },
    // Quarter of the year
    q(d) { return ~~(d.getUTCMonth() / 3) + 1; },
    // RFC 2822 formatted date
    r(d, l) { return __global__.format(d, 'D, d M Y H:i:s O', l); },
    // Seconds, with leading zeros
    s(d) { return __global__.pad(d.getUTCSeconds()); },
    // English ordinal suffix for the day of the month, 2 characters
    S(d) {
      const a = d.getUTCDate() % 10; const
        b = d.getUTCDate() % 100;
      return (a === 1) && (b !== 11) && 'st'
             || (a === 2) && (b !== 12) && 'nd'
             || (a === 3) && (b !== 13) && 'rd' || 'th';
    },
    // Number of days in the given month
    t(d) { return __global__.daysInMonth(d); },
    // Time zone abbreviation
    T(d) { return 'UTC'; },
    // Microseconds
    u(d) { return __global__.pad(d.getUTCMilliseconds(), 6); },
    // Seconds since the Unix Epoch
    U(d) { return ~~(d / 1000); },
    // Numeric representation of the day of the week
    w(d) { return d.getUTCDay(); },
    // ISO-8601 week number of year, weeks starting on Monday
    W(d) { return __global__.pad(__global__.isocalendar(d)[1]); },
    // A short numeric representation of a year, 2 digits
    y(d) { return (`${d.getUTCFullYear()}`).substr(2); },
    // A full numeric representation of a year, 4 digits
    Y(d) { return d.getUTCFullYear(); },
    // The day of the year (starting from 0)
    z(d) { return Math.floor((d - (new Date(Date.UTC(d.getUTCFullYear(), 0, 1)))) / DAY_SIZE); },
  };

  // **************************************
  // *** *** *** module methods *** *** ***
  // **************************************

  __global__.date = function (y, m, d, h, n, s, ms) {
    if (!arguments.length) { return new Date(__global__.now()); }
    y = parseInt(y || 0, 10);
    if (arguments.length === 1) { return new Date(y); }
    const ts = Date.UTC(y, parseInt(m || 0, 10), parseInt(d || 1, 10),
      parseInt(h || 0, 10), parseInt(n || 0, 10), parseInt(s || 0, 10),
      parseInt(ms || 0, 10));
    var d = new Date(ts);
    if (y < 100 && y >= 0) { // JS date ranges 0-99 are interpreted by Date.UTC as 1900-1999
      d.setUTCFullYear(y);
    }
    return d;
  };

  // zero pad a string n to l places
  __global__.pad = function (n, l) {
    let s = __global__.pad.z;
    if (!s) { // This mess is here because JSlint breaks on new Array(999)
      const a = []; a[999] = '';
      s = __global__.pad.z = a.join('0');
    }
    s += n;
    return s.substring(s.length - (l || 2));
  };

  // is a given year a leap year
  __global__.isLeapYear = function (y) {
    if (_toString.call(y) === '[object Date]') { y = y.getUTCFullYear(); }
    return ((y % 4 === 0) && (y % 100 !== 0)) || (y % 400 === 0);
  };

  // return the number of days in a date's month
  __global__.daysInMonth = function (dt) {
    const m = dt.getMonth();
    if (m === 1) {
      return __global__.isLeapYear(dt) ? 29 : 28;
    }
    return [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31][m];
  };

  // return a 3-tuple, (ISO year, ISO week number, ISO weekday).
  __global__.isocalendar = function (dt) {
    const d = dt.getUTCDay();
    const t = new Date(dt.valueOf());
    t.setDate(t.getDate() - ((d + 6) % 7) + 3);
    const iso_year = t.getUTCFullYear();
    const w = Math.floor((t.getTime() - __global__.date(iso_year, 0, 1, -6)) / 86400000);
    return [iso_year, 1 + Math.floor(w / 7), d || 7];
  };

  __global__.isoyear = function (dt) {
    const y = dt.getUTCFullYear();
    if (y >= 0 && y <= 9999) {
      return __global__.pad(Math.abs(y), 4);
    }
    return ((y < 0) ? '-' : '+') + __global__.pad(Math.abs(y), 6);
  };

  // Allow setting multiple properties at once using object notation:
  // `mydate.set({ hour: 8, minute: 12, second: 0 });`
  __global__.set = function (dt, values) {
    if (typeof values === 'object') {
      let s = []; let n; let
        i;
      // step 1: collect a list of values to modify
      for (const key in values) {
        if (key in method_map) {
          n = method_map[key];
          s.push([values[key], n, method_size[n]]);
        }
      }
      // step 2: order values by size
      s = s.sort((a, b) => a[2] - b[2]);
      // step 3: little endian value zeroing
      for (i = 0; i < s.length; i++) {
        dt[`setUTC${s[i][1]}`](s[i][1] === 'Date' ? 1 : 0);
      }
      // step 4: big endian value setting
      for (i = s.length; i--;) {
        dt[`setUTC${s[i][1]}`](s[i][0]);
      }
    }
    return dt;
  };

  // parse a date
  __global__.parse = function (str) {
    let d;
    if (typeof str !== 'string') {
      throw new Error("dateutil parser can't parse non-strings.");
    }
    for (const dtype in date_parsers) {
      if (date_parsers[dtype].test.test(str)) {
        d = date_parsers[dtype].parse(str);
        d.type = dtype;
        d.size = d.size || 0;
        break;
      }
    }
    // default parser supports RFC and a few more, or returns an "invalid date"
    if (!d) {
      d = new Date(str);
      d.size = 0;
      d.type = 'unknown_date';
    }
    return d;
  };

  // format a date to string
  __global__.format = function (d, fmt, lang) {
    // has been moved to the Date prototype?
    if (_toString.call(this) === '[object Date]') {
      lang = fmt;
      fmt = d;
      d = this;
    } else if (_toString.call(d) !== '[object Date]') {
      throw new Error('No date passed to format.');
    }

    for (let r = [], c, l = fmt.length, i = 0; i < l; i++) {
      c = fmt.charAt(i);
      // format characters
      if (c !== '\\') {
        r.push((c in date_formatters) ? date_formatters[c](d, lang) : c);
      }
      // escaped characters & unreconized characters
      else {
        c = i < fmt.length ? fmt.charAt(++i) : c;
        r.push(c);
      }
    }
    return r.join('');
  };

  // return a Date object for the current date (0 time)
  __global__.today = function () {
    return __global__.set(__global__.date(), {
      hour: 0, minute: 0, second: 0, millisecond: 0,
    });
  };

  // return timestamp for the moment
  __global__.now = (typeof Date.now === 'function')
    ? Date.now
    : function () { return +new Date(); };

  // translation hook
  __global__._ = function (s, lang) {
    const l = lang && __global__.lang[lang];
    return (l && s in l) ? l[s] : s;
  };

  __global__.fixTimezoneOffset = function (time) {
    const date = new Date(time);
    date.setHours(date.getHours() + date.getTimezoneOffset() / 60);
    return date;
  };

  __global__.formatChina = function (time) {
    function PrefixZero(num, n) {
      return (Array(n).join(0) + num).slice(-n);
    }
    return `${time.getFullYear()}-${
      PrefixZero(time.getMonth() + 1, 2)}-${
      PrefixZero(time.getDate(), 2)} ${
      PrefixZero(time.getHours(), 2)}:${
      PrefixZero(time.getMinutes(), 2)}:${
      PrefixZero(time.getSeconds(), 2)}`;
  };

  __global__.calcDate = function (time1, time2) {
    return Math.abs(time1.getDate() - time2.getDate());
  };
}(
  (typeof module !== 'undefined' && module.exports)
    ? module.exports
    : (this.dateutil = {}),
));
