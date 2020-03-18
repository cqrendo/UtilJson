import '@polymer/polymer/polymer-legacy.js';
import '../../admin/products/dynamic-view-grid.js';
import '../../admin/products/dynamic-form.js';
import { html } from '@polymer/polymer/lib/utils/html-tag.js';
import { PolymerElement } from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-split-layout/src/vaadin-split-layout.js';
import '@vaadin/vaadin-checkbox/src/vaadin-checkbox.js';

class DynamicTreeDisplay extends PolymerElement {
  static get template() {
    return html`
<style include="shared-styles">
:host {
/* 	display: block; */
	flex-grow:1; 
/*	height: 1000px; */  
/*	width: 100%; */
/* 		 display: flex;  */
/*          flex-direction: column;  */
/*          height: 100%; */
	 
}
</style>
<div id="divQuery" style="flex:none"></div>
<div>
 <vaadin-split-layout id="gridSplitDisplay" style="height: 99%;">
  <div id="divTree">
  </div>
  <div id="divDisplayAndSubgrids">
   <vaadin-split-layout orientation="vertical">
    <div>
     <form-buttons-bar id="buttons"></form-buttons-bar>
     <div id="divDisplay"></div>
    </div>
    <div id="divSubGrid" style="flex: 1 1 667px;"></div>
   </vaadin-split-layout>
  </div>
 </vaadin-split-layout>
</div>
`;
  }

  static get is() {
      return 'dynamic-tree-display';
  }
  static get properties() {
      return {
          // Declare your properties here.
      };
  }
}
customElements.define(DynamicTreeDisplay.is, DynamicTreeDisplay);

