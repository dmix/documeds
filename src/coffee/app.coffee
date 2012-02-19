@DocuMeds = {
  Url: null, Views: {}, Controllers: {}, Collections: {}, Models: {}, Functions: {}
  init: ->
    Backbone.history.start()
    # DocuMeds.Controllers.Items.index()
    return false
}

$ ->
  DocuMeds.init()
  DocuMeds.Functions.applyDefaultText("autocomplete", "Asprin, Valium, Zanax...")

  new Autocomplete({
    input: $("#autocomplete")
    results: $('#resultsList')
  })
  # $('#dosage').modal('toggle')
  
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