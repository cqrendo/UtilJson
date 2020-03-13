import { PolymerElement } from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-grid/src/vaadin-grid.js';
import '../../../components/search-bar.js';
import '../../../components/utils-mixin.js';
import '../../../../styles/shared-styles.js';
import '@vaadin/vaadin-grid-pro/src/vaadin-grid-pro.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';
import { html } from '@polymer/polymer/lib/utils/html-tag.js';
class ProductsView extends PolymerElement {
  static get template() {
    return html`
   <style include="shared-styles">
      :host {
               display: flex; 
         flex-direction: column; 
       height: 100vh; 
/* 		overflow:hidden; */
      }
    </style>  
   <vaadin-button id="newRow">
     + 
   </vaadin-button> 
   <vaadin-grid-pro id="grid" column-reordering-allowed="" page-size="50" style="margin:0px;width:100%;"></vaadin-grid-pro> 
`;
  }

  static get is() {
    return 'dynamic-view-grid';
  }
}
window.customElements.define(ProductsView.is, ProductsView);

