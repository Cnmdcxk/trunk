<!DOCTYPE html>
<html lang="en" class="gundongLineNone">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>

        <@block name="head">head</@block>
        <title><@block name='title'>title</@block></title>

        <script>
            var PAGE_PRIVILEGE_CODE = '${PrivilegeCode!}';
            var IS_LOGIN = '${UserID!}' !== '';
            var ver = '${ver!}';
            var role = '${role!}';
            var userId = '${UserID!""}'.trim();


//            //获取请求路径登入token
//            var pathParamStr = location.search.replace('?', '');
//
//            console.log('pathParamStr=',pathParamStr);
//
//            if (pathParamStr !='') {
//
//                var pathParamArr = pathParamStr.split('&');
//
//                console.log('pathParamArr=',pathParamArr);
//
//                for (var i= 0; i < pathParamArr.length; i++) {
//
//                    var arrItem = pathParamArr[i].split('=');
//                    if (arrItem[0] == 'authToken' && arrItem[1] != undefined) {
//
//                        document.cookie = 'AuthToken=' + arrItem[1];
//                        location.href = location.pathname;
//                    }
//                }
//
//            }



        </script>

        <style type="text/css">
            [v-cloak] {
                display: none;
            }
        </style>
    </head>
    <body>
        <@block name='body'>body</@block>
    </body>



</html>