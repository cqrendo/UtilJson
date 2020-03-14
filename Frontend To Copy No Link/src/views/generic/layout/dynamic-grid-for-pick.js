import '@polymer/polymer/polymer-legacy.js';
import '../../admin/products/dynamic-view-grid.js';
import '../../admin/products/dynamic-form.js';
import '@vaadin/vaadin-split-layout/src/vaadin-split-layout.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';
import '@vaadin/vaadin-split-layout/src/vaadin-split-layout.js';
import { html } from '@polymer/polymer/lib/utils/html-tag.js';
import { PolymerElement } from '@polymer/polymer/polymer-element.js';
class DynamicGridForPick extends PolymerElement {
  static get template() {
    return html`
   <style include="shared-styles">
:host {
/* 	display: block; */
	    display: flex; 
         flex-direction: column; 
         height: 100%;
 /*      height: 100%;  */
}
</style> 
   <vaadin-split-layout orientation="vertical" style="height: 100%"> 
    <div id="divQuery"></div> 
    <div style="height: 100%"> 
     <vaadin-button id="acceptPick">
       Aceptar 
     </vaadin-button> 
     <dynamic-grid id="grid"></dynamic-grid> 
    </div> 
   </vaadin-split-layout> 
`;
  }

  static get is() {
      return 'dynamic-grid-for-pick';
  }
  static get properties() {
      return {
          // Declare your properties here.
      };
  }
}
customElements.define(DynamicGridForPick.is, DynamicGridForPick);

