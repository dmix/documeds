class Autocomplete
  query: (q) ->
    results = $('#results')
    resultsList = $('#resultsList')
    if q.length > 0 and q != "Asprin, Valium, Zanax..."
      url = "/clomate/" + q
      $.ajax({
        url: url,
        contentType: "application/json",
        type: "GET",
        success: (data) ->
          # console.log(data)
          _.each(data, (result) ->
            resultsList.append("<li><a href='/medication/show/" + result["id"] + "'>" + result["name"] + "</a></li>")
          )
          results.show()
      })
    else
      resultsList.html("")
      results.hide()

this.Autocomplete = new Autocomplete;