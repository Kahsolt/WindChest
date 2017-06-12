<%--
  Created by IntelliJ IDEA.
  User: kahsolt
  Date: 17-6-1
  Time: 上午5:46
  To change this template use File | Settings | File Templates.
--%>

<%-- Windchest Page --%>
<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@ page errorPage="_error.jsp" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!-- Header Navigator -->
<%@ include file="_header.jsp"%>

<!-- Content Body -->
<article class="w3-content">
    <section class="w3-container w3-margin-top">
        <div id="console" class="w3-panel w3-border w3-border-blue w3-leftbar"></div>
    </section>
    <section class="w3-container w3-margin-top">
        <div class="w3-row">
            <div class="w3-threequarter">
                <textarea id="message" class="w3-border w3-border-blue w3-leftbar" rows="3" title="message" autofocus="autofocus"></textarea>
            </div>
            <div class="w3-quarter">
<%if(!guest) {%>
                <form action="Upload.action" target="view_frame" method="post" enctype="multipart/form-data">
                    <input name="file" class="w3-input" type="file">
                    <div class="w3-row">
                        <input id="send" class="w3-input w3-blue w3-half" type="button" onclick="send();" value="发送">
                        <input id="upload" class="w3-input w3-indigo w3-half" type="submit" value="上传">
                    </div>
                </form>
<%} else {%>
                <input id="send" class="w3-input w3-blue" type="button" onclick="send();" value="发送">
<%}%>
            </div>
        </div>
    </section>
    <section>
        <iframe name="view_frame" width="0" height="0"></iframe>
    </section>
</article>

<!-- WebSocket -->
<script>
    function $(id) { return document.getElementById(id); }

    var isGreeted=false;
    var isReconnected=false;
    var reconnectTimes=10;
    var ws=null;
    var wsUrl='ws://localhost:8080/windchest';
    //var wsUrl='ws://echo.websocket.org/echo';
    //var wsUrl='ws://windchest.kahsolt.cc:8080/windchest';

    function initWebSocket() {
        if(!window.WebSocket) {
            alert("您的浏览器不支持WebSocket，请用Chrome!!");
            return;
        } else {
            if(reconnectTimes>0) {
                reconnectTimes-=1;
                ws=new WebSocket(wsUrl);
            } else {
                if(!isReconnected) {
                    log("[System] 啊哦连接断了，请刷新页面……");
                    isReconnected=true;
                }
            }
        }
        ws.onopen=function(e) {
            if(!isGreeted) {
                log('欢迎~ Ctrl+Enter可以快速发送～');
                isGreeted=true;
            }
        };
        ws.onclose=function(e) {
            initWebSocket();
        };
        ws.onerror=function(e) {
            log("[System] Websocket错误! ",e);
            initWebSocket();
        };
        ws.onmessage=function (e) {
            log(e.data);
        };
    }
    function initFileDrag() {
        document.ondrop=function (e) {
            document.body.style.backgroundColor='#fff';
            try {
                e.preventDefault();
                //if(e.dataTransfer.files[0].type.indexOf('image') === -1)
                handleFileDrop(e.dataTransfer.files[0]);
            } catch (err) {
                log(err);
            }
        };
        document.ondragover=function (e) {
            e.preventDefault();
            document.body.style.backgroundColor='#eee';
        };
        document.ondragleave=function (e) {
            e.preventDefault();
            document.body.style.backgroundColor='#fff';
        };
    }
    function initHotKey() {
        document.onkeydown=function(e){
            var event = e || window.event || arguments.callee.caller.arguments[0];
            if (event && event.ctrlKey && event.keyCode === 13) {
                event.preventDefault();
                send();
            }
        };
    }
    function send() {
        if(ws!==null&&ws.readyState===WebSocket.OPEN) {
            var msg=$('message').value;
            if(msg!==null&&msg!=='') {
                msg=trimMessage(msg);
                if(msg!=='') {
                    ws.send(msg);
                }
                $('message').value='';
            }
        } else {
            log("[System] 正在尝试重新连接……");
            initWebSocket();
            send();
        }
    }
    function log(msg) {
        var console=$('console');
        if(msg instanceof Blob){
            var img=document.createElement("img");
            img.classList.add('w3-panel');
            img.classList.add('w3-border');
            img.classList.add('w3-border-light-blue');
            img.classList.add('w3-pale-blue');
            img.classList.add('w3-round');
            img.src=window.URL.createObjectURL(msg);
            console.appendChild(img);
        } else {
            var p=document.createElement('p');
            p.classList.add('w3-panel');
            p.classList.add('w3-border');
            p.classList.add('w3-border-light-blue');
            p.classList.add('w3-sand');
            p.classList.add('w3-round');
            p.style.wordWrap='break-word';
            p.innerHTML=msg;
            console.appendChild(p);
        }
        while(console.childNodes.length>75) {
            console.removeChild(console.firstChild);
        }
        console.scrollTop=console.scrollHeight;
    }
    function trimMessage(msg) {
        return msg.replace(/^[\s\r\n]+/,'').replace(/[\s\r\n]+$/,'');
    }
    function handleFileDrop(file) {
        var reader=new FileReader();
        reader.readAsArrayBuffer(file);
        reader.onload=function () {
            ws.send(reader.result);
            log('Sending File: '+file.name);
        };
        //Ajax上传
//        var xhr = new XMLHttpRequest();
//        xhr.open("post", "upload", true);
//        xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");
//        var fd = new FormData();
//        fd.append('file', file);
//        xhr.send(fd);
    }

    //Main Entry
    initWebSocket();
    initFileDrag();
    initHotKey();

</script>

<style>
    #console {
        height: 380px;
        word-break: break-all;
        word-wrap: break-word;
        overflow-x: auto;
        overflow-y: scroll;
    }
    #message {
        width: 100%;
        height: 100%;
        resize: none;
        word-break: break-all;
        word-wrap: break-word;
        overflow-x: auto;
        overflow-y: scroll;
    }
    /*
    #file {
        position: relative;
        display: inline-block;
        background: #D0EEFF;
        border: 1px solid #99D3F5;
        border-radius: 4px;
        padding: 4px 12px;
        overflow: hidden;
        color: #1E88C7;
        text-decoration: none;
        text-indent: 0;
    }
    #file input {
        position: relative;
        opacity: 0;
    }
    #file:hover {
        background: #AADFFD;
        border-color: #78C3F3;
        color: #004974;
        text-decoration: none;
    }
    */
</style>

<!-- Footer Copyright-->
<%@ include file="_footer.jsp"%>