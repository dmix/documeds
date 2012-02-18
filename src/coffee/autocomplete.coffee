class Autocomplete
  query: (q) ->
    results = $('#results')
    if q.length > 0 and q != "Asprin, Valium, Zanax..."
      url = "http://localhost:5000/clomate/" + q
      $.ajax({
        url: url,
        contentType: "application/json",
        type: "GET",
        success: (data) ->
          # console.log(data)
          
          _.each(data, (result) ->
            results.append("<li><a href='/medication/show/" + result["id"] + "'>" + result["name"] + "</a></li>")
          )
      })
    else
      results.html("")

this.Autocomplete = new Autocomplete;