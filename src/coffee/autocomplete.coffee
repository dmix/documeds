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
      })
    else
      @fields.results.html("")
      @inserted = []

@Autocomplete = Autocomplete