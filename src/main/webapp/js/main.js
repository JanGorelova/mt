/**
 * Created by iMac on 10/03/17.
 */
$(document).ready(function () {
    getSubscriptionTweets();
});

function getSubscriptionTweets() {
    $.post("GetSubscriptions", function (response) {
        $('#content').html(response);
    })
}

function getMyTweets() {
    $.post("GetMyTweets", function (response) {
        $('#content').html(response);
    })
}

function newTweet() {
    var tweetText = $('#tweet').val();
    $.post("NewTweet", {
        tweetText: tweetText
    }, function (response) {
        if (response.length > 0) {
            $('#tweet').val('');
            getSubscriptionTweets();
        }
    })
}

function likePressed(messageId) {
    $.post("LikePressed", {
        messageId: messageId
    }, function () {
        getSubscriptionTweets();
    })
}
