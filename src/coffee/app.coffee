@App = {
  init: ->
}
$(document).ready(->
  $("#autocomplete").observe_field(1, ->
    Autocomplete.query(this.value)
  )
  $('#results').hide()
  App.init()
)