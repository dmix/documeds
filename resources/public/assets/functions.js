(function() {
  var returnHash, returnKeys, urlParams;

  DocuMeds.Functions.getUrlParameters = function() {
    return urlParams({}, returnHash);
  };

  DocuMeds.Functions.getUrlParameter = function(param) {
    var params;
    params = DocuMeds.Functions.getUrlParameters();
    return params[param];
  };

  DocuMeds.Functions.paramsContain = function(param) {
    var keys;
    keys = urlParams([], returnKeys);
    return _.include(keys, param);
  };

  urlParams = function(vars, fn) {
    var parameters;
    parameters = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    _.each(parameters, function(param) {
      var duo;
      duo = param.split('=');
      return fn(vars, duo);
    });
    return vars;
  };

  DocuMeds.Functions.readCookie = function(name) {
    var c, ca, i, nameEQ;
    nameEQ = name + "=";
    ca = document.cookie.split(";");
    i = 0;
    while (i < ca.length) {
      c = ca[i];
      while (c.charAt(0) === " ") {
        c = c.substring(1, c.length);
      }
      if (c.indexOf(nameEQ) === 0) {
        return c.substring(nameEQ.length, c.length).replace(/"/g, '');
      }
      i++;
    }
    return ca;
  };

  DocuMeds.Functions.setCookie = function(cookieName, cookieValue) {
    var expire, today;
    today = new Date();
    expire = new Date("2015-01-01 12:00:00");
    return document.cookie = cookieName + "=" + escape(cookieValue) + ";expires=" + expire.toGMTString();
  };

  returnKeys = function(vars, duo) {
    return vars.push(duo[0]);
  };

  returnHash = function(vars, duo) {
    return vars[duo[0]] = duo[1];
  };

  DocuMeds.Functions.applyDefaultText = function(elem, val) {
    elem = document.getElementById(elem);
    if (elem === null || elem === void 0) return false;
    elem.style.color = '#9d9d9d';
    if (elem.value === '') elem.value = val;
    elem.onfocus = function() {
      if (this.value === val) {
        this.style.color = '';
        return this.value = '';
      }
    };
    return elem.onblur = function() {
      if (this.value === '') {
        this.style.color = '#9d9d9d';
        return this.value = val;
      }
    };
  };

}).call(this);
