/**
 * Some JS functions for MusicTwitter
 */
$(document).ready(function () {
    getSubscriptionTweets();
});

// Stores the current page
var currentPage = 'getSubscriptionTweets';

// Stores the number of watched pages
var pageCount = 1;

function refreshCurrentPage() {
    window[currentPage]();
}

function getNextTweets() {
    switch (currentPage) {
        case "getSubscriptionTweets":
            $.post("GetSubscriptions", {
                pageCount: pageCount
            }, function (response) {
                $('#next').remove();
                $('#content').append(response);
            });
            break;
        case "getMyTweets":
            $.post("GetMyTweets", {
                pageCount: pageCount
            }, function (response) {
                $('#next').remove();
                $('#content').append(response);
            });
            break;
        case "getInstrumentTweets":
            $.post("GetInstrumentTweets", {
                pageCount: pageCount
            }, function (response) {
                $('#next').remove();
                $('#content').append(response);
            });
            break;
        case "getCountryTweets":
            $.post("GetCountryTweets", {
                pageCount: pageCount
            }, function (response) {
                $('#next').remove();
                $('#content').append(response);
            });
            break;
    }
    pageCount++;
}

function getTweetsWithoutOffset() {
    switch (currentPage) {
        case "getSubscriptionTweets":
            $.post("GetSubscriptions", {
                pageCount: pageCount,
                resetLimit: currentPage
            }, function (response) {
                $('#content').html(response);
            });
            break;
        case "getMyTweets":
            $.post("GetMyTweets", {
                pageCount: pageCount,
                resetLimit: currentPage
            }, function (response) {
                $('#content').html(response);
            });
            break;
        case "getInstrumentTweets":
            $.post("GetInstrumentTweets", {
                pageCount: pageCount,
                resetLimit: currentPage
            }, function (response) {
                $('#content').html(response);
            });
            break;
        case "getCountryTweets":
            $.post("GetCountryTweets", {
                pageCount: pageCount,
                resetLimit: currentPage
            }, function (response) {
                $('#content').html(response);
            });
            break;
    }
}

function addNextTweets(response) {
    $('.next').remove();
    $('#content').append(response);
}

function getSubscriptionTweets() {
    resetPageCount();
    $.post("GetSubscriptions", function (response) {
        $('#content').html(response);
    });
    changeSelectedMenuItem('subscriptionMenu');
    currentPage = 'getSubscriptionTweets';
}

function getMyTweets() {
    resetPageCount();
    $.post("GetMyTweets", function (response) {
        $('#content').html(response);
    });
    changeSelectedMenuItem('myTweetsMenu');
    currentPage = 'getMyTweets';
}

function getInstrumentTweets() {
    resetPageCount();
    $.post("GetInstrumentTweets", function (response) {
        $('#content').html(response);
    });
    changeSelectedMenuItem('instrumentMenu');
    currentPage = 'getInstrumentTweets';
}

function getCountryTweets() {
    resetPageCount();
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
        getTweetsWithoutOffset();
    })
}

function subscribe(userId) {
    $.post("SubscribePressed", {
        userId: userId
    }, function () {
        getTweetsWithoutOffset();
    })
}

function unsubscribe(userId) {
    $.post("UnsubscribePressed", {
        userId: userId
    }, function () {
        getTweetsWithoutOffset();
    })
}

function resetPageCount() {
    pageCount = 1;
}