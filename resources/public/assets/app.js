
  this.DocuMeds = {
    Url: null,
    Views: {},
    Controllers: {},
    Collections: {},
    Models: {},
    Functions: {},
    init: function() {
      Backbone.history.start();
      return false;
    }
  };

  $(function() {
    var render, select;
    DocuMeds.init();
    DocuMeds.Functions.applyDefaultText("autocomplete", "Asprin, Valium, Zanax...");
    render = function(term, data, type) {
      return term;
    };
    select = function(term, data, type) {
      return console.log("Selected " + term);
    };
    $('#autocomplete').soulmate({
      url: "/autocomplete/",
      types: ["medication"],
      renderCallback: render,
      selectCallback: select,
      minQueryLength: 2,
      maxResults: 5
    });
    $('.numUp').live('click', function() {
      var el, val;
      el = $(this).parent().prev('input');
      val = new String(parseInt(el.val()) + 1);
      el.val(val[0]);
      return false;
    });
    return $('.numDown').live('click', function() {
      var el, num, val;
      el = $(this).prev().parent().prev('input');
      num = parseInt(el.val());
      if (num !== 0) {
        val = new String(num - 1);
        el.val(val[0]);
      }
      return false;
    });
  });
