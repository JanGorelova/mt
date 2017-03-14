/**
 * Created by iMac on 10/03/17.
 */
$(document).ready(function () {
    getSubscriptionTweets();
});

var currentPage = 'getSubscriptionTweets';

function refreshCurrentPage() {
    window[currentPage]();
}

function getSubscriptionTweets() {
    $.post("GetSubscriptions", function (response) {
        $('#content').html(response);
    });
    changeSelectedMenuItem('subscriptionMenu');
    currentPage = 'getSubscriptionTweets';
}

function getMyTweets() {
    $.post("GetMyTweets", function (response) {
        $('#content').html(response);
    });
    changeSelectedMenuItem('myTweetsMenu');
    currentPage = 'getMyTweets';
}

function getInstrumentTweets() {
    $.post("GetInstrumentTweets", function (response) {
        $('#content').html(response);
    });
    changeSelectedMenuItem('instrumentMenu');
    currentPage = 'getInstrumentTweets';
}

function getCountryTweets() {
    $.post("GetCountryTweets", function (response) {
        $('#content').html(response);
    });
    changeSelectedMenuItem('countryMenu');
    currentPage = 'getCountryTweets';
}

function changeSelectedMenuItem(item) {
    $('a.menu').css('border', '');
    $('#' + item).css('border-bottom', 'solid 5px #22aaff');
}

function newTweet() {
    var tweetText = $('#tweet').val();
    $.post("NewTweet", {
        tweetText: tweetText
    }, function (response) {
        if (response.length > 0) {
            $('#tweet').val('');
            getMyTweets();
        }
    })
}

function likePressed(messageId) {
    $.post("LikePressed", {
        messageId: messageId
    }, function () {
        refreshCurrentPage();
    })
}

function subscribe(userId) {
    $.post("SubscribePressed", {
        userId: userId
    }, function () {
        refreshCurrentPage();
    })
}

function unsubscribe(userId) {
    $.post("UnsubscribePressed", {
        userId: userId
    }, function () {
        refreshCurrentPage();
    })
}