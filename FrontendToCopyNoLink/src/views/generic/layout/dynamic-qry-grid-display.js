import '@polymer/polymer/polymer-legacy.js';
import '../../admin/products/dynamic-view-grid.js';
import '../../admin/products/dynamic-form.js';
import '@vaadin/vaadin-split-layout/src/vaadin-split-layout.js';
import { html } from '@polymer/polymer/lib/utils/html-tag.js';
import { PolymerElement } from '@polymer/polymer/polymer-element.js';
import {ThemableMixin} from '@vaadin/vaadin-themable-mixin/vaadin-themable-mixin.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';
import {afterNextRender} from '@polymer/polymer/lib/utils/render-status.js';


class DynamicQryGridDisplay extends ThemableMixin(PolymerElement) {
  static get template() {
    return html`
<style include="shared-styles">
:host {
 	display: inline-block;
	flex-grow:1; 

 /*   height: 1000px; */  
	width: 100%; 
/* 		 display: flex;  */
/*          flex-direction: column;  */
          height: 100%; 
	 
}
</style>
<div>
 <vaadin-button theme="icon" aria-label="Qry" id="showHideQuery" on-click="_showHideQuery" style="display:inline-block;">
  <iron-icon icon="lumo:search"></iron-icon>
 </vaadin-button>
 <div id="divTitle" style="display:inline-block;">
 </div>
</div>
<div id="querySplitGrid">
 <div id="divQuery" style="flex:none; background:#dbdced;"></div>
 <div style="height: 100%;">
  <vaadin-split-layout id="gridSplitDisplay" style="height: 100%;">
   <dynamic-grid id="grid"></dynamic-grid>
   <div id="divDisplayAndSubgrids">
    <vaadin-split-layout orientation="vertical" id="displaySplitSubGrid" style="height: 100%;">
     <div>
      <form-buttons-bar id="buttons"></form-buttons-bar>
      <div id="divDisplay"></div>
     </div>
     <div id="divSubGrid"></div>
    </vaadin-split-layout>
   </div>
  </vaadin-split-layout>
 </div>
</div>
`;
  }
  constructor() {
    super();
    // When possible, use afterNextRender to defer non-critical
    // work until after first paint.
    // it set the inicial height depending on divQuery height 
    afterNextRender(this, function() {
      var elmnt =  this.$.divQuery;
      var vheight = elmnt.offsetHeight+60;
 //     alert(" afterRender -> " +vheight);
      var c = 'calc(100% - '+vheight+'px)';
      this.$.querySplitGrid.style.setProperty('height', c);
  
    });
  }

//ready() {
//  super.ready();
//  // it set the inicial height depending on divQuery height 
// 
//  
// var elmnt =  this.$.divQuery;
// var vheight = elmnt.offsetHeight;
//  alert(" H -> " +vheight);
// var c = 'calc(100% - '+vheight+'px)';
// this.$.querySplitGrid.style.setProperty('height', c);
//
//}
  static get is() {
      return 'dynamic-qry-grid-display';
  }
  static get properties() {
      return {
          // Declare your properties here.
      };
  }
   _showHideQuery() {

var dQ =  this.$.divQuery;
var vheight = dQ.offsetHeight;
//alert(" H -> " +vheight);
var c;
if (vheight == 0)
	{	
	this.$.divQuery.style.setProperty('display', 'block');
	vheight = dQ.offsetHeight+60;
	c = 'calc(100% - '+vheight+'px)';
	}
else
	{
	c = 'calc(100% - 60px)';
	this.$.divQuery.style.setProperty('display', 'none');	
	}
this.$.querySplitGrid.style.setProperty('height', c);
 // document.getElementById('querySplitGrid').style.setProperty('height', c);


}
}
customElements.define(DynamicQryGridDisplay.is, DynamicQryGridDisplay);

