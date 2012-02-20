@DocuMeds = {
  Views: {}, Controllers: {}, Collections: {}, Models: {}, Functions: {}
  init: ->
    Backbone.history.start()
}

$ ->
  DocuMeds.init()
  DocuMeds.Functions.applyDefaultText("autocomplete", "Asprin, Valium, Zanax...")

  render = (term, data, type) -> term
  select = (term, data, type) -> console.log("Selected #{term}")

  $('#autocomplete').soulmate({
    url:            "/autocomplete/"
    types:          ["medication"]
    renderCallback: render
    selectCallback: select
    minQueryLength: 2
    maxResults:     5
  })

  $('.numUp').live('click', ->
    el = $(this).parent().prev('input')
    val = new String(parseInt(el.val()) + 1)
    el.val(val[0])
    return false
  )

  $('.numDown').live('click', ->
    el = $(this).prev().parent().prev('input')
    num = parseInt(el.val())
    if num != 0
      val = new String(num - 1)
      el.val(val[0])
    return false
  )