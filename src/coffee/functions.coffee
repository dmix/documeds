DocuMeds.Functions.getUrlParameters = ->
  # return object containing url parameters for example ?one=two&three=four will return {one: two, three: four}
  urlParams({}, returnHash)

DocuMeds.Functions.getUrlParameter = (param) ->
  # check if url contains a parameter and return it
  params = DocuMeds.Functions.getUrlParameters()
  params[param]

DocuMeds.Functions.paramsContain = (param) ->
  # check if url includes a parameter
  keys = urlParams([], returnKeys)
  _.include(keys, param)

urlParams = (vars, fn) ->
  # iterate over the parameters in the url and apply function
  parameters = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&')
  _.each(parameters, (param) ->
    duo = param.split('=') # ['key', 'value']
    fn(vars, duo)
  )
  vars

DocuMeds.Functions.readCookie = (name) ->
  nameEQ = name + "="
  ca = document.cookie.split(";")
  i = 0
  while i < ca.length
    c = ca[i]
    c = c.substring(1, c.length)  while c.charAt(0) is " "
    return c.substring(nameEQ.length, c.length).replace(/"/g, '')  if c.indexOf(nameEQ) is 0
    i++
  ca

DocuMeds.Functions.setCookie = (cookieName, cookieValue) ->
   today = new Date()
   expire = new Date("2015-01-01 12:00:00")
   document.cookie = cookieName + "=" + escape(cookieValue) + ";expires=" + expire.toGMTString();

returnKeys = (vars, duo) ->
  vars.push(duo[0])

returnHash = (vars, duo) ->
  vars[duo[0]] = duo[1]
  
DocuMeds.Functions.applyDefaultText = (elem, val) ->
  # insert placeholder text into input and hide when clicked
  elem = document.getElementById(elem)
  if elem == null or elem == undefined
    return false

  elem.style.color = '#9d9d9d'

  if elem.value == ''
    elem.value = val;

  elem.onfocus = ->
    if this.value == val
      this.style.color = ''
      this.value = ''

  elem.onblur = ->
    if this.value == ''
      this.style.color = '#9d9d9d'
      this.value = val