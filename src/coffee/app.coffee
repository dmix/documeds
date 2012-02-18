@DocuMeds = {
  Url: null,
  Views: {},
  Controllers: {},
  Collections: {},
  Models: {},
  Functions: {}
  init: ->
    return false
}

$ ->
  DocuMeds.init()
  $("#autocomplete").observe_field(1, ->
    Autocomplete.query(this.value)
  )
  DocuMeds.Functions.applyDefaultText("autocomplete", "Asprin, Valium, Zanax...")