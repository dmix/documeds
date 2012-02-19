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
          _.each(data, (result) ->
            if _.indexOf(that.inserted, result["id"]) == -1
              dust.render("autocomplete_result", result, (err, output) ->
                that.fields.results.append(output)
                that.inserted.push(result["id"])
              )
          )
          that.fields.input.removeClass('loading')
      })
    else
      @fields.results.html("")
      @fields.input.removeClass('loading')
      @inserted = []

@Autocomplete = Autocomplete