class ItemsController extends Backbone.Router
  routes: {
    "items" : "index"
    "items/add/:id/:name" : "add"
  },

  index: ->
    if DocuMeds.Collections.Items.length == 0
      DocuMeds.Collections.Items.fetch(
        success: ->
          view = new DocuMeds.Views.Items({
            collection: DocuMeds.Collections.Items
            el : $('#itemList')
          })
          view.render()
      )
    else
      view = new DocuMeds.Views.Items({collection: DocuMeds.Collections.Items})
      view.render()

  add: (id, name) ->
    dust.render("items_dosage", {id: id, name: name}, (err, output) ->
      $('#modal').html(output)
      $('#dosage').modal({
        backdrop: true,
        show:true
      })
      $('#medName').jTruncate({  
          length: 10,
          minTrail: 0,
          ellipsisText: "..."
      })
    )

  create: (id) ->
    DocuMeds.Collections.Items.create({medication_id: id}, {wait: true})

DocuMeds.Controllers.Items = new ItemsController


class Item extends Backbone.Model
  defaults : {}
  name: 'item'
  initialize: ->
    cid = @cid
    this.set({
      cid: cid
    })

  url: ->
    '/items'

DocuMeds.Models.Item = Item


class Items extends Backbone.Collection
  model: DocuMeds.Models.Item

  url: ->
    '/items'

DocuMeds.Collections.Items = new Items


class ItemView extends Backbone.View
  initialize: (options) ->
    @render = _.bind(@render, this); 
    @model.bind('change:name', @render);

  render: ->
    that = this
    dust.render("items_row", @model.toJSON(), (err, output) ->
      that.el = output
    )
    return this


class ItemsView extends Backbone.View
  initialize: ->
    that = this
    _(this).bindAll('add', 'remove')
    @_itemViews = []
    @collection.each(this.add);
    @collection.bind('add', @add)
    @collection.bind('remove', @remove)

  add: (item) ->
    v = new ItemView({
      model : item      
    })

    @_itemViews.push(v)
    if @_rendered
      $(this.el).append(v.render().el)

  remove: (model) ->
    viewToRemove = _(@_itemViews).select((v)->
      return cv.model == model
    )[0]
    @_itemViews = _(@_itemViews).without(viewToRemove);
 
    if @_rendered
      $(viewToRemove.el).remove()

  render: ->
    that = this
    @_rendered = true
    $(this.el).empty()
    _(@._itemViews).each((dv) ->
      $(that.el).append(dv.render().el)
    )
    return this

DocuMeds.Views.Items = ItemsView
