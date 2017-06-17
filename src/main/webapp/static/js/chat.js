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
        var className = $(this).attr('class');
        var span = '<span data-before="\\' + emojiMap[className].text
            + '" data-class="' + className
            + '">\\' + emojiMap[className].text + '</span>&nbsp;';
        messgaeInput.append(span);
    });

    // @
    $(document).on('click', 'ul.dropdown-menu li.at', function () {
        var a = '<a href="javascript:void(0)" data-before="@' + $(this).html() + '">@' + $(this).html() + '</a>&nbsp;';
        messgaeInput.append(a);
    });

    $(document).on('focus', '[contenteditable]', function () {
        var $this = $(this);
        $(this).data('before', $this.html());
    }).on('input', '[contenteditable]', function () {
        var $this = $(this);
        if ($this.data('before') !== $this.html()) {
            $this.data('before', $this.html());

            $.each($(this).find('span'), function (i, span) {
                if ($(span).data('before') !== $(span).html()) {
                    $(span).remove();
                }
            });

            $.each($(this).find('a'), function (i, a) {
                if ($(a).data('before') !== $(a).html()) {
                    $(a).remove();
                }
            });
        }
    });

    $(document).keydown(function (e) {
        if (e.ctrlKey && e.which == 13) {
            sendBtn.trigger('click');
        }
    });

    sendBtn.click(function () {
        if (messgaeInput.html()) {
            var message = {
                userId: uid.val(),
                content: messgaeInput.html()
            };
            dwrChat.onSendMessage(message);
            messgaeInput.html('').focus();
        }
    });

    $.getJSON('static/emoji.json', function (emojiArray) {
        var ulHtml = '<li>';
        $.each(emojiArray, function (i, emoji) {
            emojiMap[emoji.className] = emoji;
            ulHtml += '<span class="' + emoji.className + '" style="cursor: pointer"></span>';
        });
        ulHtml += '</li>';
        container.find('ul.dropdown-menu').last().html(ulHtml);
    })

});

function showMsg(message) {
    var obj = JSON.parse(message);
    // 显示消息
    var panel = container.find('.panel-area .panel-body');
    panel.append('<span class="label label-info">' + obj.userId + '</span>&nbsp;' + obj.timestamp + '<br>'
        + parseContent(obj.content) + '<br>').scrollTop(425);
    // 系统消息时更新在线用户
    if (obj.userId == "System") {
        parseUsers(obj.userIds);
    }
}

/*
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
 */

function parseContent(content) {
    $.each($('<div>' + content + '</div>').find('span'), function (i, span) {
        var outerHTML = $(span).prop('outerHTML');
        $(span).addClass($(span).data('class'));
        $(span).removeAttr('data-before');
        $(span).removeAttr('data-class');
        $(span).html('');
        content = content.replace(outerHTML, $(span).prop('outerHTML'));
    });
    return content;
}

function parseUsers(userIds) {
    var ulHtml = '';
    var count = 0;
    $.each(userIds, function (i, userId) {
        ulHtml += '<li style="margin-left: 20px;cursor: pointer" class="at">' + userId + '</li>';
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