import { PolymerElement } from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-tabs/src/vaadin-tabs.js';
import '@vaadin/vaadin-tabs/src/vaadin-tab.js';
import '@vaadin/vaadin-icons/vaadin-icons.js';
import '../../styles/shared-styles.js';
import { html } from '@polymer/polymer/lib/utils/html-tag.js';
class AppNavigation extends PolymerElement {
  static get template() {
    return html`
   <style include="shared-styles">
      :host {
        display: block;
        box-shadow: 0 0 16px 2px var(--lumo-shade-20pct);
        z-index: 200;
        --iron-icon-width: 16px;
      }

      .toolbar {
        display: flex;
        justify-content: center;
        background-color: var(--lumo-base-color);
        align-items: center;
        position: relative;
      }

      vaadin-tab {
        font-size: var(--lumo-font-size-xs);
        padding-left: .75em;
        padding-right: .75em;
      }

      vaadin-tabs {
        overflow: hidden;
      }

      .branding {
        display: none;
      }

      .navigation-tabs a {
        text-decoration: none;
      }

      @media (min-width: 600px) {
        vaadin-tab {
          font-size: var(--lumo-font-size-m);
          padding-left: 1em;
          padding-right: 1em;
        }
      }

      @media (min-width: 1000px) {
        .branding {
          display: block;
          font-size: var(--lumo-font-size-m);
          line-height: normal;
          position: absolute;
          left: 0;
          padding-left: var(--lumo-space-m);
        }
      }
    </style> 
   <div class="toolbar"> 
    <div class="branding">
      tys 
    </div> 
    <vaadin-tabs id="tabs"></vaadin-tabs> 
   </div> 
`;
  }

  static get is() {
    return 'app-navigation';
  }
}

window.customElements.define(AppNavigation.is, AppNavigation);

