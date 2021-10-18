import '@polymer/polymer/polymer-legacy.js';
import '../../admin/products/dynamic-view-grid.js';
import '../../admin/products/dynamic-form.js';
import '@vaadin/vaadin-split-layout/src/vaadin-split-layout.js';
import '@vaadin/vaadin-split-layout/src/vaadin-split-layout.js';
import { html } from '@polymer/polymer/lib/utils/html-tag.js';
import { PolymerElement } from '@polymer/polymer/polymer-element.js';
import {ThemableMixin} from '@vaadin/vaadin-themable-mixin/vaadin-themable-mixin.js';
//class DynamicGridDisplay extends PolymerElement {
class DynamicGridDisplay extends ThemableMixin(PolymerElement) {
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
     <vaadin-split-layout id="gridSplitDisplay" style="height: 99%;" > 
      	<dynamic-grid id="grid"></dynamic-grid> 
      	<div id="divDisplayAndSubgrids"> 
       		<vaadin-split-layout id="gridDisplaySubGrid" orientation="vertical" > 
        	<div> 
    			<form-buttons-bar id="buttons"></form-buttons-bar> 
    		<div id="divDisplay"></div> 
    		</div> 
        	<div id="divSubGrid" style="flex: 1 1 667px;" ></div> 
      		</vaadin-split-layout> 
      	</div> 
     </vaadin-split-layout> 
`;
  }

  static get is() {
      return 'dynamic-grid-display';
  }
  static get properties() {
      return {
          // Declare your properties here.
      };
  }
}
customElements.define(DynamicGridDisplay.is, DynamicGridDisplay);

