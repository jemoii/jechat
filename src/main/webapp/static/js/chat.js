var container = $('.container');

var uid = container.find('#uid');

var messgaeInput = container.find('#messageInput');
var sendBtn = container.find('#sendBtn');

var emojiMap = {};

$(function () {
    dwr.engine.setActiveReverseAjax(true);
    dwr.engine.setNotifyServerOnPageUnload(true);

    uid.blur(function () {
        if (uid.val()) {
            if (/[\w\d_]+/.test(uid.val())) {
                dwrChat.onPageLoad(uid.val());
                sendBtn.removeAttr('disabled');

                uid.attr('readonly', 'readonly');
                uid.parent().find('.success-block').removeClass('hidden');
                uid.parent().removeClass('has-warning').addClass('has-success');
            } else {
                uid.parent().find('.warning-block').removeClass('hidden');
                uid.parent().removeClass('has-success').addClass('has-warning');
            }
        }
    });
    uid.click(function () {
        if (uid.val()) {
            if (/[\w\d_]+/.test(uid.val())) {
                dwrChat.onPageClose(uid.val());
                sendBtn.attr('disabled', 'disabled');
            }

            uid.val('').removeAttr('readonly');
            uid.parent().find('span').addClass('hidden');
            uid.parent().removeClass('has-warning').removeClass('has-success');
        }
    });

    refreshHead();

    // emoji
    $(document).on('click', 'ul.dropdown-menu li span.eq', function () {
        var tag = $(this).attr('class').substring("eq mp0 ".length);
        sendMsg("&" + tag + "&");
    });

    // @
    $(document).on('click', 'ul.dropdown-menu li span.at', function () {
        sendMsg("&@" + $(this).html() + "&");
    });

    sendBtn.click(function () {
        if (messgaeInput.val()) {
            sendMsg(messgaeInput.val());
            messgaeInput.val('').focus();
        }
    });

    $.getJSON('static/emoji.json', function (emojiArray) {
        var ulHtml = '<li>';
        $.each(emojiArray, function (i, emoji) {
            emojiMap[emoji.tag] = emoji;
            ulHtml += '<span class="' + emoji.className + '" style="cursor: pointer"></span>';
        });
        ulHtml += '</li>';
        container.find('ul.dropdown-menu').last().html(ulHtml);
    })

});

function sendMsg(content) {
    var message = {
        userId: uid.val(),
        content: content
    };
    dwrChat.onSendMessage(message);
}

function showMsg(message) {
    var obj = JSON.parse(message);
    // 显示消息
    var panel = container.find('.panel-area .panel-body');
    panel.append(obj.userId + ': ' + parseContent(obj.content) + '<br>').scrollTop(425);
    // 系统消息时更新在线用户
    if (obj.userId == "System") {
        parseUsers(obj.userIds);
    }
}

function parseContent(content) {
    var reg = /&[^&]+&/;
    content.replace(reg, function (s, index) {
        var s_;
        if (s) {
            if (s.charAt(1) == '@') {
                // @消息
                s_ = '<a href="javascript:void(0)">' + s.substring(1, s.length - 1) + '</a>'
            } else {
                // emoji消息
                s_ = '<span class="' + emojiMap[s.substring(1, s.length - 1)].className + '"></span>';
            }
        }
        content = content.substring(0, index) + s_ + content.substring(index + s.length);
    });
    return content;
}

function parseUsers(userIds) {
    var ulHtml = '';
    var count = 0;
    $.each(userIds, function (i, userId) {
        ulHtml += '<li><span style="margin-left: 20px;cursor: pointer" class="at">' + userId + '</span></li>';
        count++;
    });
    ulHtml += '<li role="separator" class="divider"></li>';
    ulHtml += '<li><label style="margin-left: 5px">' + count + ' Online</label></li>';
    container.find('ul.dropdown-menu').first().html(ulHtml);
}

var pointCount = 0;
var pointArray = [".", "..", "...", "....", "....."];
function refreshHead() {
    container.find('.panel-area .panel-heading')
        .html('Chatting <strong>' + pointArray[pointCount] + '</strong>');

    pointCount++;
    // min: 1, max: 5
    if (pointCount == 5) {
        pointCount = 0;
    }

    // interval 1s
    setTimeout('refreshHead()', 1000);
}