
  this.DocuMeds = {
    Views: {},
    Controllers: {},
    Collections: {},
    Models: {},
    Functions: {},
    init: {}
  };

  $(function() {
    $('#survey').modal({
      backdrop: true,
      show: true
    });
    return $('#early').click(function() {
      return $('#survey').modal('show');
    });
  });
