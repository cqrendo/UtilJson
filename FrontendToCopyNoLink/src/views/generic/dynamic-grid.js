import { PolymerElement } from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-grid/src/vaadin-grid.js';
import '../../components/search-bar.js';
import '../../components/utils-mixin.js';
import '../../../styles/shared-styles.js';
import '@vaadin/vaadin-grid-pro/src/vaadin-grid-pro.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';
import { html } from '@polymer/polymer/lib/utils/html-tag.js';
import '@vaadin/flow-frontend/file-download-wrapper.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-horizontal-layout.js';

class DynamicGrid extends PolymerElement {
  static get template() {
    return html`
<style include="shared-styles">
      :host {
      	height: 75%; 
        display: flex; 
        flex-direction: column; 
    	
 /* 	overflow:hidden; */
      }
    </style>
 <vaadin-horizontal-layout>
<div id="divExporter">
 ..
</div>
<div id="itemButtons">
 <vaadin-button id="newRow" theme="raised primary">
   nueva lin. 
 </vaadin-button>
 <vaadin-button id="deleteRow" theme="raised tertiary error">
   elimina lin. 
 </vaadin-button>
</div>
</vaadin-horizontal-layout>
<vaadin-grid-pro id="grid" column-reordering-allowed=""></vaadin-grid-pro>
`;
  }

  static get is() {
    return 'dynamic-grid';
  }
}
window.customElements.define(DynamicGrid.is, DynamicGrid);

