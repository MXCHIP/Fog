/*
 * User Management JavaScript Library
 * Copyright (c) 2016 mxchip.com
 */
(function(window) {

    var f = {};
    const _URLHEAD = 'https://v2dev.fogcloud.io/enduser';
    const _GETUSERTOKEN = 'https://v2dev.fogcloud.io/wechat/getusertoken/';
    const _UNBINDDEV = 'https://v2dev.fogcloud.io/wechat/unbindevice/';
    const _GETSIGN = 'https://v2dev.fogcloud.io/wechat/createjssdk/?url=';

    const _REFJWT = _URLHEAD + '/refreshToken/';
    const _GETDEVLIST = _URLHEAD + '/devicelistbyenduser/';
    const _GETUSERINFO = _URLHEAD + '/getUserInfo/';
    const _GETDEVINFO_HEAD = _URLHEAD + '/deviceInfo/?deviceid=';
    const _UPDATEDEVNAME = _URLHEAD + '/updateDeviceAlias/';
    const _SENDCMDURL = _URLHEAD + '/sendCommandAdv/';


    const _FORMAT = 'json';
    const _FLAG = 3;

    /**
     * Get an jwt by appid and wxcode.
     */
    f.getAcctoken = function(appid, code, callback) {
        var data = {
            "appid": appid,
            "code": code
        };
        fogHttp(_GETUSERTOKEN, 'POST', data, '', callback);
    };

    /**
     * Get userinfo.
     */
    f.getUserInfo = function(jwt, callback) {
        fogHttp(_GETUSERINFO, 'GET', '', jwt, callback);
    };

    /**
     * Refresh the token, or it will overdue after 7 days.
     */
    f.refreshToken = function(jwt, callback) {
        var data = {
            "token": jwt
        };
        fogHttp(_REFJWT, 'POST', data, '', callback);
    };

    /**
     * Get the list of devices for the specified user.
     */
    f.getDeviceList = function(jwt, callback) {
        fogHttp(_GETDEVLIST, 'GET', '', jwt, callback);
    };

    /**
     * Get the detail of specified devices.
     */
    f.getDeviceInfo = function(deviceid, jwt, callback) {
        var fgurl = _GETDEVINFO_HEAD + deviceid;
        fogHttp(fgurl, 'GET', '', jwt, callback);
    };

    /**
     * Update the alias of specified devices.
     */
    f.updateDeviceAlias = function(deviceid, alias, jwt, callback) {
        var data = {
            "deviceid": deviceid,
            "alias": alias
        };
        fogHttp(_UPDATEDEVNAME, 'PUT', data, jwt, callback);
    };

    /**
     * Get signature from wechat service.
     */
    f.getSignature = function(signUrl, jwt, callback) {
        fogHttp(_GETSIGN + signUrl, 'GET', '', jwt, callback);
    };

    /**
     * Unbind specified devices.
     */
    f.unBindDevice = function(deviceid, appid, jwt, callback) {
        var data = {
            "deviceid": deviceid,
            "appid": appid
        };
        fogHttp(_UNBINDDEV, 'POST', data, jwt, callback);
    };

    /**
     * Send payload to device.
     */
    f.sendCommand = function(deviceid, devicepw, payload, jwt, callback) {
        var data = {
            "deviceid": deviceid,
            "devicepw": devicepw,
            "payload": payload,
            "format": _FORMAT,
            "flag": _FLAG
        };
        fogHttp(_SENDCMDURL, 'POST', data, jwt, callback);
    };

    /**
     * The interface of HTTP request.
     */
    function fogHttp(url, type, data, jwt, callback) {
        var headers = {
            'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'
        };

        if (checkPara(jwt)) {
            headers['Authorization'] = "JWT " + jwt;
        }

        $.ajax({
            url: url,
            type: type,
            data: data,
            headers: headers,
            success: function(ret) {
                callback(ret, null);
            },
            error: function(err) {
                callback(null, err);
            }
        });
    }

    /**
     * Check the paraments is null or empty.
     */
    function checkPara() {
        var n = arguments.length;
        if (n < 1) {
            return false;
        } else {
            for (var i = 0; i < n; i++) {
                if (arguments[i] == "" || arguments[i] == null)
                    return false;
            }
            return true;
        }
    }

    window.Fog = f;

})(window);
