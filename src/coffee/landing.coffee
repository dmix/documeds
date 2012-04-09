@DocuMeds = { Views: {}, Controllers: {}, Collections: {}, Models: {}, Functions: {}, init: {} }

$ ->
  $('#survey').modal({
    backdrop: true,
    show:true
  })
  $('#early').click(->
    $('#survey').modal('show')
  )