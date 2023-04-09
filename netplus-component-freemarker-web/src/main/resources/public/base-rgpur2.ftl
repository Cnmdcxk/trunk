<@override name="head">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${cdn}/js/3rd/elementui/index.2.12.0.css">
    <link rel="stylesheet" href="${cdn}/css/iconfont/iconfont.css?v=${ver}">
    <link rel="stylesheet" href="${cdn}/css/base.css?v=${ver}">
    <link rel="shortcut icon" href="${cdn}/img/logo/logo.png">
    <@block name="css"></@block>
    <script src="${cdn}/js/3rd/jquery/jquery-3.4.1.min.js"></script>
    <script src="${cdn!}/js/3rd/polyfill/browser.min.js"></script>
    <script src="${cdn!}/js/3rd/polyfill/polyfill.min.js"></script>
    <script src="${cdn}/js/3rd/vue/vue.min.js"></script>
    <script src="${cdn}/js/3rd/vue/axios.min.js"></script>
    <script src="${cdn}/js/3rd/vue/vue.extend.js?v=${ver}"></script>
    <script src="${cdn}/js/3rd/elementui/index.2.12.0.min.js"></script>
    <script src="${cdn}/js/framework/ajax.js?v=${ver}"></script>
    <script src="${cdn}/js/framework/dac.js?v=${ver}"></script>
    <script src="${cdn}/js/framework/components/money.js"></script>

</@override>

<@override name="body">
    <#include "/top2.ftl">
    <@block name="content"></@block>
    <@block name="js"></@block>
    <#include "/go-top.ftl">
    
</@override>

<@extends name="/base.ftl"/>



