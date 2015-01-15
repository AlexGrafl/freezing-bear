$.ajax({

    url: 'http://your-app.com/api/news/12',
    dataType: 'JSONp',
    success: function(data, status) {
        $.each(data, function(key, value){
            //handle the data               
       });
    },
    error: function() {
        //handle the error
    }
}
