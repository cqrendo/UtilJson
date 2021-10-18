import '@polymer/polymer/polymer-legacy.js';
import '../../admin/products/dynamic-view-grid.js';
import '../../admin/products/dynamic-form.js';
import '@vaadin/vaadin-split-layout/src/vaadin-split-layout.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';
import '@vaadin/vaadin-split-layout/src/vaadin-split-layout.js';
import { html } from '@polymer/polymer/lib/utils/html-tag.js';
import { PolymerElement } from '@polymer/polymer/polymer-element.js';
class DynamicDisplayForAskData extends PolymerElement {
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
    <div id="divDisplay"  style="overflow: unset;"></div> 
    <div style="height: 100%"> 
     <vaadin-button id="acceptDataAndContinue">
       Confirmar y continuar 
     </vaadin-button> 
    </div> 
`;
  }

  static get is() {
      return 'dynamic-display-for-ask-data';
  }
  static get properties() {
      return {
          // Declare your properties here.
      };
  }
}
customElements.define(DynamicDisplayForAskData.is, DynamicDisplayForAskData);

