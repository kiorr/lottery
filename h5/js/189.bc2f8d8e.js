(self["webpackChunkactivity"]=self["webpackChunkactivity"]||[]).push([[189],{1229:function(t,e,n){"use strict";n.r(e),n.d(e,{default:function(){return Z}});var i=function(){var t=this,e=t._self._c;t._self._setupProxy;return e("main",{staticClass:"detail-page"},[e("GoBack"),2===t.info.status?e("Activity",{attrs:{remainder:t.remainder},on:{activityEndFun:t.activityEndFun}}):t._e(),0===t.info.status?e("div",{staticClass:"status-mask"},[e("div",{staticClass:"card-box not-start"},[e("img",{attrs:{src:n(2982),alt:""}}),e("p",[t._v("开始时间")]),e("p",[t._v(t._s(t.info.startTimeStr))])])]):t._e(),1===t.info.status?e("div",{staticClass:"status-mask"},[t._m(0)]):t._e(),e("div",{staticClass:"right-float"},[2===t.info.status?e("p",{on:{click:t.openMyPrizeFun}},[t._v("我的奖品")]):t._e(),e("p",{on:{click:t.toRule}},[t._v("活动规则")])]),e("div",{staticClass:"page-footer",class:1===t.info.status||2===t.info.status?"ended":"not-start"}),e("div",{directives:[{name:"show",rawName:"v-show",value:t.myPrizeFlag,expression:"myPrizeFlag"}],staticClass:"my-prize-mask"},[e("div",{staticClass:"my-prize-cont"},[e("div",{staticClass:"popup-title"},[t._v("我的奖品")]),e("i",{staticClass:"popup-close",on:{click:function(e){t.myPrizeFlag=!1}}}),e("div",{staticClass:"popup-cont-mask"},[t.myPrizeList&&t.myPrizeList.length>0?e("ul",{staticClass:"prize-list"},t._l(t.myPrizeList,(function(n){return e("li",{key:n.id},[e("span",[t._v(t._s(n.name||"-"))]),e("span",[t._v(t._s(n.hittime?n.hittime.slice(0,11):"-"))])])})),0):e("div",{staticClass:"default-page"},[e("p",[t._v("很遗憾，您未中奖")])])])])])],1)},o=[function(){var t=this,e=t._self._c;t._self._setupProxy;return e("div",{staticClass:"card-box ended"},[e("img",{attrs:{src:n(2130),alt:""}}),e("p",[t._v("活动已结束")])])}],s=n(6318),a=(n(560),n(4582)),r=n(2782),c=function(){var t=this,e=t._self._c;t._self._setupProxy;return e("div",{staticClass:"activity-cont"},[e("div",{directives:[{name:"show",rawName:"v-show",value:t.countdownFlag,expression:"countdownFlag"}],staticClass:"countdown-mask"},[e("div",{staticClass:"countdown-cont"},[e("img",{staticClass:"countdown-title",attrs:{src:n(7038),alt:""}}),e("img",{staticClass:"countdown-num",attrs:{src:n(1982)(`./icon-${t.countdownIndex}.png`),alt:""}})])]),e("div",{directives:[{name:"show",rawName:"v-show",value:!t.countdownFlag,expression:"!countdownFlag"}],staticClass:"remainder"},[e("h1",[t._v("剩余时间")]),e("p",[t._v(t._s(t.remainderTime)),e("span",[t._v("s")])])]),t.countdownFlag?t._e():e("div",{ref:"gameCont",staticClass:"game-cont"},t._l(t.gameList,(function(n){return e("div",{key:n.id,staticClass:"envelope-item",style:{left:n.left,transform:`scale(${n.scale}) rotate(${n.rotate}deg)`},on:{click:function(e){return t.receiveFun(n,e)}}})})),0)])},l=[],u=n(521),d=function(t,e,n,i){var o,s=arguments.length,a=s<3?e:null===i?i=Object.getOwnPropertyDescriptor(e,n):i;if("object"===typeof Reflect&&"function"===typeof Reflect.decorate)a=Reflect.decorate(t,e,n,i);else for(var r=t.length-1;r>=0;r--)(o=t[r])&&(a=(s<3?o(a):s>3?o(e,n,a):o(e,n))||a);return s>3&&a&&Object.defineProperty(e,n,a),a};let h=class extends r.w3{constructor(...t){super(...t),(0,s.Z)(this,"userInfo",{}),(0,s.Z)(this,"countdownFlag",!1),(0,s.Z)(this,"countdownTime",null),(0,s.Z)(this,"countdownIndex",1),(0,s.Z)(this,"gameList",[]),(0,s.Z)(this,"currIndex",1),(0,s.Z)(this,"remainderTime",0),(0,s.Z)(this,"time",null),(0,s.Z)(this,"remainder",void 0)}mounted(){this.getUserInfo()}beforeDestroy(){clearInterval(this.countdownTime),this.countdownTime=null,clearInterval(this.time),this.time=null}getUserInfo(){a.Z.get("/user/info").then((t=>{if(1===t.data.code){const{data:e}=t.data;this.userInfo=e,localStorage.getItem(`${e.id}-${this.$route.query.id}`)?this.gameFun():(this.countdownFun(),localStorage.setItem(`${e.id}-${this.$route.query.id}`,"true"))}else this.$router.push({name:"login"})}))}gameFun(){this.getActivityTime(this.remainder),this.countdownTime=setInterval((()=>{const t=this.currIndex;let e={id:this.currIndex,rotate:Math.floor(100*Math.random()-50),left:Math.floor(600*Math.random())/75+"rem",scale:.65*Math.random()+.65};this.gameList.push(e),setTimeout((()=>{this.gameList=this.gameList.filter((e=>e.id!==t))}),2e3),this.currIndex++}),400)}getActivityTime(t){this.remainderTime=Math.ceil(t/1e3),this.time=setInterval((()=>{this.remainderTime--,0===this.remainderTime&&(clearInterval(this.countdownTime),this.countdownTime=null,clearInterval(this.time),this.time=null,this.$emit("activityEndFun"))}),1e3)}receiveFun(t,e){a.Z.get(`act/go/${this.$route.query.id}`).then((n=>{const{data:i,code:o,msg:s}=n.data;1===o?(this.hasPrizeFun(i,e.target,!0),this.gameList=this.gameList.filter((e=>e.id!==t.id))):0===o?(this.hasPrizeFun(i,e.target,!1),this.gameList=this.gameList.filter((e=>e.id!==t.id))):(0,u.Z)(s)}))}hasPrizeFun(t,e,n){e.style.animation="";const i=document.getElementsByClassName("game-cont")[0],o=document.createElement("div");n?(o.className="receive-tips",o.innerHTML=t.name):(o.className="receive-tips not-receive-tips",o.innerHTML=""),o.style.top=e.offsetTop+"px",o.style.left=e.offsetLeft+"px",i.appendChild(o),setTimeout((()=>{i.removeChild(o)}),1e3)}countdownFun(){4==this.countdownIndex?setTimeout((()=>{this.countdownTime=null,this.countdownFlag=!1,this.gameFun()}),1e3):(this.countdownFlag=!0,setTimeout((()=>{this.countdownIndex++,this.countdownFun()}),1e3))}};d([(0,r.fI)()],h.prototype,"remainder",void 0),h=d([r.wA],h);var f=h,m=f,v=n(1001),p=(0,v.Z)(m,c,l,!1,null,"6a0295dc",null),g=p.exports,y=n(7774),k=n(1602),C=n.n(k),w=function(t,e,n,i){var o,s=arguments.length,a=s<3?e:null===i?i=Object.getOwnPropertyDescriptor(e,n):i;if("object"===typeof Reflect&&"function"===typeof Reflect.decorate)a=Reflect.decorate(t,e,n,i);else for(var r=t.length-1;r>=0;r--)(o=t[r])&&(a=(s<3?o(a):s>3?o(e,n,a):o(e,n))||a);return s>3&&a&&Object.defineProperty(e,n,a),a};let x=class extends r.w3{constructor(...t){super(...t),(0,s.Z)(this,"info",{}),(0,s.Z)(this,"currId",this.$route.query.id),(0,s.Z)(this,"myPrizeFlag",!1),(0,s.Z)(this,"myPrizeList",[]),(0,s.Z)(this,"remainder",0)}mounted(){this.currId?this.getGameInfo():this.$router.push("/")}getGameInfo(){a.Z.get(`/game/info/${this.currId}`).then((t=>{if(1===t.data.code){const{data:e,now:n}=t.data,i=new Date(n).getTime(),o=new Date(e.starttime).getTime(),s=new Date(e.endtime).getTime();this.remainder=s-i,1===e.status&&i>=o&&i<s&&(e.status=2),e.startTimeStr=C()(e.starttime).format("YYYY年MM月DD日 HH:mm:ss"),this.info=e}}))}openMyPrizeFun(){this.myPrizeFlag=!0,a.Z.get("/user/hit/-1/1/100").then((t=>{if(1===t.data.code){const{data:e}=t.data;e.totalNum&&e.items.length>0&&(this.myPrizeList=e.items)}}))}toRule(){this.$router.push({name:"rule",query:{id:this.currId}})}activityEndFun(){this.info.status=1}};x=w([(0,r.wA)({components:{Activity:g,GoBack:y.Z}})],x);var b=x,I=b,$=(0,v.Z)(I,i,o,!1,null,"1ec39d5e",null),Z=$.exports},4674:function(t,e,n){"use strict";n.d(e,{e:function(){return O}});n(560);var i={zIndex:2e3,lockCount:0,stack:[],find:function(t){return this.stack.filter((function(e){return e.vm===t}))[0]},remove:function(t){var e=this.find(t);if(e){e.vm=null,e.overlay=null;var n=this.stack.indexOf(e);this.stack.splice(n,1)}}},o=n(7378),s=n(2715),a=n.n(s),r=n(161),c=n(868),l=n(8745),u=n(6472),d=(0,r.d)("overlay"),h=d[0],f=d[1];function m(t){(0,u.PF)(t,!0)}function v(t,e,n,i){var s=(0,o.Z)({zIndex:e.zIndex},e.customStyle);return(0,c.Xq)(e.duration)&&(s.animationDuration=e.duration+"s"),t("transition",{attrs:{name:"van-fade"}},[t("div",a()([{directives:[{name:"show",value:e.show}],style:s,class:[f(),e.className],on:{touchmove:e.lockScroll?m:c.ZT}},(0,l.ED)(i,!0)]),[null==n.default?void 0:n.default()])])}v.props={show:Boolean,zIndex:[Number,String],duration:[Number,String],className:null,customStyle:Object,lockScroll:{type:Boolean,default:!0}};var p=h(v),g=n(3147),y={className:"",customStyle:{}};function k(t){return(0,l.LI)(p,{on:{click:function(){t.$emit("click-overlay"),t.closeOnClickOverlay&&(t.onClickOverlay?t.onClickOverlay():t.close())}}})}function C(t){var e=i.find(t);if(e){var n=t.$el,s=e.config,a=e.overlay;n&&n.parentNode&&n.parentNode.insertBefore(a.$el,n),(0,o.Z)(a,y,s,{show:!0})}}function w(t,e){var n=i.find(t);if(n)n.config=e;else{var o=k(t);i.stack.push({vm:t,config:e,overlay:o})}C(t)}function x(t){var e=i.find(t);e&&(e.overlay.show=!1)}function b(t){var e=i.find(t);e&&((0,g.Z)(e.overlay.$el),i.remove(t))}var I=n(5185),$=n(9579);function Z(t){return"string"===typeof t?document.querySelector(t):t()}function P(t){var e=void 0===t?{}:t,n=e.ref,i=e.afterPortal;return{props:{getContainer:[String,Function]},watch:{getContainer:"portal"},mounted:function(){this.getContainer&&this.portal()},methods:{portal:function(){var t,e=this.getContainer,o=n?this.$refs[n]:this.$el;e?t=Z(e):this.$parent&&(t=this.$parent.$el),t&&t!==o.parentNode&&t.appendChild(o),i&&i.call(this)}}}}var S=n(8886),T={mixins:[(0,S.X)((function(t,e){this.handlePopstate(e&&this.closeOnPopstate)}))],props:{closeOnPopstate:Boolean},data:function(){return{bindStatus:!1}},watch:{closeOnPopstate:function(t){this.handlePopstate(t)}},methods:{onPopstate:function(){this.close(),this.shouldReopen=!1},handlePopstate:function(t){if(!this.$isServer&&this.bindStatus!==t){this.bindStatus=t;var e=t?u.on:u.S1;e(window,"popstate",this.onPopstate)}}}},_={transitionAppear:Boolean,value:Boolean,overlay:Boolean,overlayStyle:Object,overlayClass:String,closeOnClickOverlay:Boolean,zIndex:[Number,String],lockScroll:{type:Boolean,default:!0},lazyRender:{type:Boolean,default:!0}};function O(t){return void 0===t&&(t={}),{mixins:[$.D,T,P({afterPortal:function(){this.overlay&&C()}})],provide:function(){return{vanPopup:this}},props:_,data:function(){return this.onReopenCallback=[],{inited:this.value}},computed:{shouldRender:function(){return this.inited||!this.lazyRender}},watch:{value:function(e){var n=e?"open":"close";this.inited=this.inited||this.value,this[n](),t.skipToggleEvent||this.$emit(n)},overlay:"renderOverlay"},mounted:function(){this.value&&this.open()},activated:function(){this.shouldReopen&&(this.$emit("input",!0),this.shouldReopen=!1)},beforeDestroy:function(){b(this),this.opened&&this.removeLock(),this.getContainer&&(0,g.Z)(this.$el)},deactivated:function(){this.value&&(this.close(),this.shouldReopen=!0)},methods:{open:function(){this.$isServer||this.opened||(void 0!==this.zIndex&&(i.zIndex=this.zIndex),this.opened=!0,this.renderOverlay(),this.addLock(),this.onReopenCallback.forEach((function(t){t()})))},addLock:function(){this.lockScroll&&((0,u.on)(document,"touchstart",this.touchStart),(0,u.on)(document,"touchmove",this.onTouchMove),i.lockCount||document.body.classList.add("van-overflow-hidden"),i.lockCount++)},removeLock:function(){this.lockScroll&&i.lockCount&&(i.lockCount--,(0,u.S1)(document,"touchstart",this.touchStart),(0,u.S1)(document,"touchmove",this.onTouchMove),i.lockCount||document.body.classList.remove("van-overflow-hidden"))},close:function(){this.opened&&(x(this),this.opened=!1,this.removeLock(),this.$emit("input",!1))},onTouchMove:function(t){this.touchMove(t);var e=this.deltaY>0?"10":"01",n=(0,I.Ob)(t.target,this.$el),i=n.scrollHeight,o=n.offsetHeight,s=n.scrollTop,a="11";0===s?a=o>=i?"00":"01":s+o>=i&&(a="10"),"11"===a||"vertical"!==this.direction||parseInt(a,2)&parseInt(e,2)||(0,u.PF)(t,!0)},renderOverlay:function(){var t=this;!this.$isServer&&this.value&&this.$nextTick((function(){t.updateZIndex(t.overlay?1:0),t.overlay?w(t,{zIndex:i.zIndex++,duration:t.duration,className:t.overlayClass,customStyle:t.overlayStyle}):x(t)}))},updateZIndex:function(t){void 0===t&&(t=0),this.$el.style.zIndex=++i.zIndex+t},onReopen:function(t){this.onReopenCallback.push(t)}}}}},521:function(t,e,n){"use strict";n.d(e,{Z:function(){return S}});n(560);var i=n(7378),o=n(7195),s=n(161),a=n(868),r=0;function c(t){t?(r||document.body.classList.add("van-toast--unclickable"),r++):(r--,r||document.body.classList.remove("van-toast--unclickable"))}var l=n(4674),u=n(6975),d=n(8372),h=(0,s.d)("toast"),f=h[0],m=h[1],v=f({mixins:[(0,l.e)()],props:{icon:String,className:null,iconPrefix:String,loadingType:String,forbidClick:Boolean,closeOnClick:Boolean,message:[Number,String],type:{type:String,default:"text"},position:{type:String,default:"middle"},transition:{type:String,default:"van-fade"},lockScroll:{type:Boolean,default:!1}},data:function(){return{clickable:!1}},mounted:function(){this.toggleClickable()},destroyed:function(){this.toggleClickable()},watch:{value:"toggleClickable",forbidClick:"toggleClickable"},methods:{onClick:function(){this.closeOnClick&&this.close()},toggleClickable:function(){var t=this.value&&this.forbidClick;this.clickable!==t&&(this.clickable=t,c(t))},onAfterEnter:function(){this.$emit("opened"),this.onOpened&&this.onOpened()},onAfterLeave:function(){this.$emit("closed")},genIcon:function(){var t=this.$createElement,e=this.icon,n=this.type,i=this.iconPrefix,o=this.loadingType,s=e||"success"===n||"fail"===n;return s?t(u.Z,{class:m("icon"),attrs:{classPrefix:i,name:e||n}}):"loading"===n?t(d.Z,{class:m("loading"),attrs:{type:o}}):void 0},genMessage:function(){var t=this.$createElement,e=this.type,n=this.message;if((0,a.Xq)(n)&&""!==n)return"html"===e?t("div",{class:m("text"),domProps:{innerHTML:n}}):t("div",{class:m("text")},[n])}},render:function(){var t,e=arguments[0];return e("transition",{attrs:{name:this.transition},on:{afterEnter:this.onAfterEnter,afterLeave:this.onAfterLeave}},[e("div",{directives:[{name:"show",value:this.value}],class:[m([this.position,(t={},t[this.type]=!this.icon,t)]),this.className],on:{click:this.onClick}},[this.genIcon(),this.genMessage()])])}}),p=n(3147),g={icon:"",type:"text",mask:!1,value:!0,message:"",className:"",overlay:!1,onClose:null,onOpened:null,duration:2e3,iconPrefix:void 0,position:"middle",transition:"van-fade",forbidClick:!1,loadingType:void 0,getContainer:"body",overlayStyle:null,closeOnClick:!1,closeOnClickOverlay:!1},y={},k=[],C=!1,w=(0,i.Z)({},g);function x(t){return(0,a.Kn)(t)?t:{message:t}}function b(t){return document.body.contains(t)}function I(){if(a.sk)return{};if(k=k.filter((function(t){return!t.$el.parentNode||b(t.$el)})),!k.length||C){var t=new(o.ZP.extend(v))({el:document.createElement("div")});t.$on("input",(function(e){t.value=e})),k.push(t)}return k[k.length-1]}function $(t){return(0,i.Z)({},t,{overlay:t.mask||t.overlay,mask:void 0,duration:void 0})}function Z(t){void 0===t&&(t={});var e=I();return e.value&&e.updateZIndex(),t=x(t),t=(0,i.Z)({},w,y[t.type||w.type],t),t.clear=function(){e.value=!1,t.onClose&&(t.onClose(),t.onClose=null),C&&!a.sk&&e.$on("closed",(function(){clearTimeout(e.timer),k=k.filter((function(t){return t!==e})),(0,p.Z)(e.$el),e.$destroy()}))},(0,i.Z)(e,$(t)),clearTimeout(e.timer),t.duration>0&&(e.timer=setTimeout((function(){e.clear()}),t.duration)),e}var P=function(t){return function(e){return Z((0,i.Z)({type:t},x(e)))}};["loading","success","fail"].forEach((function(t){Z[t]=P(t)})),Z.clear=function(t){k.length&&(t?(k.forEach((function(t){t.clear()})),k=[]):C?k.shift().clear():k[0].clear())},Z.setDefaultOptions=function(t,e){"string"===typeof t?y[t]=e:(0,i.Z)(w,t)},Z.resetDefaultOptions=function(t){"string"===typeof t?y[t]=null:(w=(0,i.Z)({},g),y={})},Z.allowMultiple=function(t){void 0===t&&(t=!0),C=t},Z.install=function(){o.ZP.use(v)},o.ZP.prototype.$toast=Z;var S=Z},3147:function(t,e,n){"use strict";function i(t){var e=t.parentNode;e&&e.removeChild(t)}n.d(e,{Z:function(){return i}})},1982:function(t,e,n){var i={"./icon-1.png":5729,"./icon-2.png":1828,"./icon-3.png":4415,"./icon-4.png":5401,"./icon-prize.png":662};function o(t){var e=s(t);return n(e)}function s(t){if(!n.o(i,t)){var e=new Error("Cannot find module '"+t+"'");throw e.code="MODULE_NOT_FOUND",e}return i[t]}o.keys=function(){return Object.keys(i)},o.resolve=s,t.exports=o,o.id=1982},5729:function(t,e,n){"use strict";t.exports=n.p+"img/icon-1.e6fa1d1b.png"},1828:function(t,e,n){"use strict";t.exports=n.p+"img/icon-2.64238b21.png"},4415:function(t,e,n){"use strict";t.exports=n.p+"img/icon-3.74b28fab.png"},5401:function(t,e,n){"use strict";t.exports=n.p+"img/icon-4.0593caf0.png"},662:function(t,e,n){"use strict";t.exports=n.p+"img/icon-prize.9fbfe2ad.png"},7038:function(t,e,n){"use strict";t.exports=n.p+"img/pic-countdown.8bb5471a.png"},2130:function(t,e,n){"use strict";t.exports=n.p+"img/pic-ended.d652870d.png"},2982:function(t,e,n){"use strict";t.exports=n.p+"img/pic-not-start.41b2c748.png"}}]);
//# sourceMappingURL=189.bc2f8d8e.js.map