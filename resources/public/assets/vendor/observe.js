// jquery.observe_field.js
(function( $ ){
  jQuery.fn.observe_field = function(frequency, callback) {
    frequency = frequency * 50; // translate to milliseconds

    return this.each(function(){
      var $this = $(this);
      var prev = $this.val();
      var check = function() {
        var val = $this.val();
        if(prev != val){
          prev = val;
          $this.map(callback); // invokes the callback on $this
        }
      };
      var reset = function() {
        if(ti){
          clearInterval(ti);
          ti = setInterval(check, frequency);
        }
      };
      check();
      var ti = setInterval(check, frequency); // invoke check periodically
      // reset counter after user interaction
      $this.bind('keyup click mousemove', reset); //mousemove is for selects
    });

  };
})( jQuery );


(function($){
  $.fn.jTruncate = function(options) {
     
    var defaults = {
      length: 300,
      minTrail: 20,
      ellipsisText: "..."
    };
    
    var options = $.extend(defaults, options);
     
    return this.each(function() {
      obj = $(this);
      var body = obj.html();
      
      if(body.length > options.length + options.minTrail) {
        var splitLocation = body.indexOf(' ', options.length);
        if(splitLocation != -1) {
          // truncate tip
          var splitLocation = body.indexOf(' ', options.length);
          var str1 = body.substring(0, splitLocation);
          obj.html(str1 + '<span class="truncate_ellipsis">' + options.ellipsisText + '</span>');
        }
      }
      
    });
  };
})(jQuery);