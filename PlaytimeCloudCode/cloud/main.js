
// Send PlayRequest
Parse.Cloud.define("sendPlaytimeRequest", function(request, response) {
  var queryUser = new Parse.Query(Parse.Installation);
  queryUser.equalTo('username', request.params.username);

  var requestMessage = "Play Request from ";

  Parse.Push.send({
    where: queryUser,
    data: {
      action: "com.traviswooten.playtime.play.request",
      requestId: request.params.requestId,
      message: requestMessage.concat(request.params.requestUsername)
    }
  }, {
    success: function() {
      response.success("Request sent.");
    },
    error: function(error) {
      // Handle error
    }
  });
});

//After save of Playtime request, check if accepted value ahas changed to true.
Parse.Cloud.afterSave("PlayRequest", function(request) {
    if(request.object.get("accepted") == true) {

        var queryUser1 = new Parse.Query(Parse.Installation);
        queryUser1.equalTo('username', request.object.get("requestUser"));
        var queryUser2 = new Parse.Query(Parse.Installation);
        queryUser2.equalTo('username', request.object.get("receiveUser"));
        var queryUsers = Parse.Query.or(queryUser1, queryUser2);

        Parse.Push.send({
            where: queryUsers,
            data: {
              action: "com.traviswooten.playtime.play.request.accepted",
              requestId: request.object.id
            }
          }, {
            success: function() {
              //Success
            },
            error: function(error) {
              // Handle error
            }
          });
    }
});

//After save of StartPlay object, check if both users are pressing button.
Parse.Cloud.afterSave("StartPlay", function(request) {
    if((request.object.get("requesterPushed") == true) && (request.object.get("receiverPushed") == true)) {

        var PlayRequest = Parse.Object.extend("PlayRequest");
        var query = new Parse.Query(PlayRequest);
        query.equalTo("objectId", request.object.get("requestId"));
        query.find({
          success: function(results) {

            var queryUser1 = new Parse.Query(Parse.Installation);
            queryUser1.equalTo('username', results[0].get("requestUser"));
            var queryUser2 = new Parse.Query(Parse.Installation);
            queryUser2.equalTo('username', results[0].get("receiveUser"));
            var queryUsers = Parse.Query.or(queryUser1, queryUser2);

            Parse.Push.send({
                where: queryUsers,
                data: {
                  action: "com.traviswooten.playtime.start.play",
                  requestId: request.object.get("requestId"),
                  url: results[0].get("url")
                }
              }, {
                success: function() {
                  //Success
                },
                error: function(error) {
                  // Handle error
                }
              });

          },
          error: function(error) {
            alert("Error: " + error.code + " " + error.message);
          }
        });
    }
});

//After save of PlayPrepared object, check if both users are prepared.
Parse.Cloud.afterSave("PlayPrepared", function(request) {
    if((request.object.get("requesterPrepared") == true) && (request.object.get("receiverPrepared") == true)) {

        var PlayRequest = Parse.Object.extend("PlayRequest");
        var query = new Parse.Query(PlayRequest);
        query.equalTo("objectId", request.object.get("requestId"));
        query.find({
          success: function(results) {

            var queryUser1 = new Parse.Query(Parse.Installation);
            queryUser1.equalTo('username', results[0].get("requestUser"));
            var queryUser2 = new Parse.Query(Parse.Installation);
            queryUser2.equalTo('username', results[0].get("receiveUser"));
            var queryUsers = Parse.Query.or(queryUser1, queryUser2);

            Parse.Push.send({
                where: queryUsers,
                data: {
                  action: "com.traviswooten.playtime.play.prepared",
                  requestId: request.object.get("requestId")
                }
              }, {
                success: function() {
                  //Success
                },
                error: function(error) {
                  // Handle error
                }
              });

          },
          error: function(error) {
            alert("Error: " + error.code + " " + error.message);
          }
        });
    }
});
