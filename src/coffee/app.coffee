@DocuMeds = {
  Url: null,
  Views: {},
  Controllers: {},
  Collections: {},
  Models: {},
  Functions: {}
  init: ->
    Backbone.history.start()
    if window.location.hash.length == 0
      DocuMeds.Controllers.Items.index()
    return false
}

$ ->
  DocuMeds.init()
  DocuMeds.Functions.applyDefaultText("autocomplete", "Asprin, Valium, Zanax...")

  new Autocomplete({
    input: $("#autocomplete")
    results: $('#resultsList')
  })