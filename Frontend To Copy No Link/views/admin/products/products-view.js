import { PolymerElement } from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-grid/src/vaadin-grid.js';
import '../../../components/search-bar.js';
import '../../../components/utils-mixin.js';
import '../../../../styles/shared-styles.js';
import { html } from '@polymer/polymer/lib/utils/html-tag.js';
class ProductsView extends PolymerElement {
  static get template() {
    return html`
   <style include="shared-styles">
      :host {
        display: flex;
        flex-direction: column;
        height: 100vh;
      }
    </style> 
   <search-bar id="search"></search-bar> 
   <vaadin-grid id="grid" theme="crud"></vaadin-grid> 
`;
  }

  static get is() {
    return 'products-view';
  }
}

window.customElements.define(ProductsView.is, ProductsView);

