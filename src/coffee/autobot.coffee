class Autocomplete
  constructor: (@fields)->
    @inserted = []
    @observe(@fields.input)

  observe: (field) ->
    that = this
    field.observe_field(1, ->
      that.query(this.value)
    )

  query: (q) ->
    that = this
    @fields.input.addClass('loading')
    if q.length > 0 and q != "Asprin, Valium, Zanax..."
      url = "/autocomplete/" + q
      $.ajax({
        url: url,
        contentType: "application/json",
        type: "GET",
        success: (data) ->
          if data != null
            that.hideList()
            console.log(data)
            that.fields.results.show()
            insertHTML = ""
            _.each(data, (result) ->
              if _.indexOf(that.inserted, result["id"]) == -1
                dust.render("autocomplete_result", result, (err, output) ->
                  insertHTML += output
                  that.inserted.push(result["id"])
                )
            )
            that.fields.resultsList.html(insertHTML)
            that.fields.input.removeClass('loading')
      })
    else
      @hideList()

  hideList: ->
    @fields.resultsList.html("")
    @fields.input.removeClass('loading')
    @inserted = []
    @fields.results.hide()

@Autocomplete = Autocomplete