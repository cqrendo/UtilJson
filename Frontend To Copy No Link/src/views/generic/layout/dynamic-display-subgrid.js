import '@polymer/polymer/polymer-legacy.js';
import '../../admin/products/dynamic-view-grid.js';
import '../../admin/products/dynamic-form.js';
import '@vaadin/vaadin-split-layout/src/vaadin-split-layout.js';
import { html } from '@polymer/polymer/lib/utils/html-tag.js';
import { PolymerElement } from '@polymer/polymer/polymer-element.js';
import {ThemableMixin} from '@vaadin/vaadin-themable-mixin/vaadin-themable-mixin.js';

//class DynamicGridDisplay extends PolymerElement {
class DynamicDisplaySubgrid extends ThemableMixin(PolymerElement) {
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
 <div>
   <div id="divDisplayAndSubgrids">
    <vaadin-split-layout orientation="vertical" id="displaySplitSubGrid">
     <div>
      <form-buttons-bar id="buttons"></form-buttons-bar>
      <div id="divDisplay"></div>
     </div>
     <div id="divSubGrid"></div>
    </vaadin-split-layout>
   </div>
 </div>
`;
  }

  static get is() {
      return 'dynamic-display-subgrid';
  }
  static get properties() {
      return {
          // Declare your properties here.
      };
  }
}
customElements.define(DynamicDisplaySubgrid.is, DynamicDisplaySubgrid);

