(function() {
  var Item, ItemView, Items, ItemsController, ItemsView;
  var __hasProp = Object.prototype.hasOwnProperty, __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor; child.__super__ = parent.prototype; return child; };

  ItemsController = (function() {

    __extends(ItemsController, Backbone.Router);

    function ItemsController() {
      ItemsController.__super__.constructor.apply(this, arguments);
    }

    ItemsController.prototype.routes = {
      "items": "index",
      "items/add/:id/:name": "add"
    };

    ItemsController.prototype.index = function() {
      var view;
      if (DocuMeds.Collections.Items.length === 0) {
        return DocuMeds.Collections.Items.fetch({
          success: function() {
            var view;
            view = new DocuMeds.Views.Items({
              collection: DocuMeds.Collections.Items,
              el: $('#itemList')
            });
            return view.render();
          }
        });
      } else {
        view = new DocuMeds.Views.Items({
          collection: DocuMeds.Collections.Items
        });
        return view.render();
      }
    };

    ItemsController.prototype.add = function(id, name) {
      return dust.render("items_dosage", {
        id: id,
        name: name
      }, function(err, output) {
        $('#modal').html(output);
        $('#dosage').modal({
          backdrop: true,
          show: true
        });
        return $('#medName').jTruncate({
          length: 10,
          minTrail: 0,
          ellipsisText: "..."
        });
      });
    };

    ItemsController.prototype.create = function(id) {
      return DocuMeds.Collections.Items.create({
        medication_id: id
      }, {
        wait: true
      });
    };

    return ItemsController;

  })();

  DocuMeds.Controllers.Items = new ItemsController;

  Item = (function() {

    __extends(Item, Backbone.Model);

    function Item() {
      Item.__super__.constructor.apply(this, arguments);
    }

    Item.prototype.defaults = {};

    Item.prototype.name = 'item';

    Item.prototype.initialize = function() {
      var cid;
      cid = this.cid;
      return this.set({
        cid: cid
      });
    };

    Item.prototype.url = function() {
      return '/items';
    };

    return Item;

  })();

  DocuMeds.Models.Item = Item;

  Items = (function() {

    __extends(Items, Backbone.Collection);

    function Items() {
      Items.__super__.constructor.apply(this, arguments);
    }

    Items.prototype.model = DocuMeds.Models.Item;

    Items.prototype.url = function() {
      return '/items';
    };

    return Items;

  })();

  DocuMeds.Collections.Items = new Items;

  ItemView = (function() {

    __extends(ItemView, Backbone.View);

    function ItemView() {
      ItemView.__super__.constructor.apply(this, arguments);
    }

    ItemView.prototype.initialize = function(options) {
      this.render = _.bind(this.render, this);
      return this.model.bind('change:name', this.render);
    };

    ItemView.prototype.render = function() {
      var that;
      that = this;
      dust.render("items_row", this.model.toJSON(), function(err, output) {
        return that.el = output;
      });
      return this;
    };

    return ItemView;

  })();

  ItemsView = (function() {

    __extends(ItemsView, Backbone.View);

    function ItemsView() {
      ItemsView.__super__.constructor.apply(this, arguments);
    }

    ItemsView.prototype.initialize = function() {
      var that;
      that = this;
      _(this).bindAll('add', 'remove');
      this._itemViews = [];
      this.collection.each(this.add);
      this.collection.bind('add', this.add);
      return this.collection.bind('remove', this.remove);
    };

    ItemsView.prototype.add = function(item) {
      var v;
      v = new ItemView({
        model: item
      });
      this._itemViews.push(v);
      if (this._rendered) return $(this.el).append(v.render().el);
    };

    ItemsView.prototype.remove = function(model) {
      var viewToRemove;
      viewToRemove = _(this._itemViews).select(function(v) {
        return cv.model === model;
      })[0];
      this._itemViews = _(this._itemViews).without(viewToRemove);
      if (this._rendered) return $(viewToRemove.el).remove();
    };

    ItemsView.prototype.render = function() {
      var that;
      that = this;
      this._rendered = true;
      $(this.el).empty();
      _(this._itemViews).each(function(dv) {
        return $(that.el).append(dv.render().el);
      });
      return this;
    };

    return ItemsView;

  })();

  DocuMeds.Views.Items = ItemsView;

}).call(this);
