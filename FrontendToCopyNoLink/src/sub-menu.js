import '@polymer/polymer/polymer-legacy.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-horizontal-layout.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';
import { html } from '@polymer/polymer/lib/utils/html-tag.js';
import { PolymerElement } from '@polymer/polymer/polymer-element.js';
import './sub-submenu.js';

class SubMenu extends PolymerElement {
  static get template() {
    return html`
<style include="shared-styles">
                :host {
                    display: block;
                    width: 100%;
                    height: 100%;
    				overflow-y: auto;
                }
            </style>
<div id="dvTabs" style="width:100%;background:#d2d2d2;"></div>
<div id="dvPages" style="width:100%;"></div>
`;
  }

  static get is() {
      return 'sub-menu';
  }
  static get properties() {
      return {
          // Declare your properties here. style="width: 100%; height: 100%;margin:100px;">
      };
  }
}
//       customElements.define(TestView.is, TestView);
customElements.define(SubMenu.is, SubMenu);

