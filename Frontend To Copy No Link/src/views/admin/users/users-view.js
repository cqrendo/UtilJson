import { PolymerElement } from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-grid/src/vaadin-grid.js';
import '../../../components/search-bar.js';
import '../../../components/utils-mixin.js';
import '../../../../styles/shared-styles.js';
import { html } from '@polymer/polymer/lib/utils/html-tag.js';
class UsersView extends PolymerElement {
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
    return 'users-view';
  }

  ready() {
    super.ready();

    // This method is overridden to measure the page load performance and can be safely removed
    // if there is no need for that
    const grid = this.$.grid;
    let partsLoaded = 0;
    const listener = () => {
      if (++partsLoaded === 2 && window.performance.mark) {
        // the 'loading-changed' event is fired separately for the headers (first)
        // and for the content (second). Waiting for the second time
        window.performance.mark('bakery-page-loaded');
        grid.removeEventListener('loading-changed', listener);
      }
    };
    grid.addEventListener('loading-changed', listener);
  }
}

window.customElements.define(UsersView.is, UsersView);

