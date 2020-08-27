import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-combo-box/src/vaadin-combo-box-dropdown-wrapper.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-horizontal-layout.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';
import '@polymer/iron-icon/iron-icon.js';
import '@vaadin/vaadin-combo-box/src/vaadin-combo-box.js';

class ComboTwinForPick extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<vaadin-vertical-layout>
 <vaadin-horizontal-layout class="content" id="vlComboTwin1">
  <vaadin-button theme="icon" aria-label="Remove" id="dummy">
   <iron-icon icon="lumo:arrow.down"></iron-icon>
  </vaadin-button>
  <vaadin-combo-box id="cbChild1" label="CHILD"></vaadin-combo-box>
  <vaadin-combo-box id="cBParent1" label="PARENT"></vaadin-combo-box>
 </vaadin-horizontal-layout>
 <vaadin-horizontal-layout class="content" id="vlComboTwin2">
  <vaadin-button theme="icon" aria-label="Remove" id="removeCombi2">
   <iron-icon icon="lumo:minus"></iron-icon>
  </vaadin-button>
  <vaadin-combo-box id="cbChild2"></vaadin-combo-box>
  <vaadin-combo-box id="cBParent2"></vaadin-combo-box>
 </vaadin-horizontal-layout>
 <vaadin-horizontal-layout class="content" id="vlComboTwin3">
  <vaadin-button theme="icon" aria-label="Remove" id="removeCombi3">
   <iron-icon icon="lumo:minus"></iron-icon>
  </vaadin-button>
  <vaadin-combo-box id="cbChild3"></vaadin-combo-box>
  <vaadin-combo-box id="cBParent3"></vaadin-combo-box>
 </vaadin-horizontal-layout>
 <vaadin-horizontal-layout class="content" id="vlComboTwin4">
  <vaadin-button theme="icon" aria-label="Remove" id="removeCombi4">
   <iron-icon icon="lumo:minus"></iron-icon>
  </vaadin-button>
  <vaadin-combo-box id="cbChild4"></vaadin-combo-box>
  <vaadin-combo-box id="cBParent4"></vaadin-combo-box>
 </vaadin-horizontal-layout>
 <vaadin-horizontal-layout class="content" id="vlComboTwin5">
  <vaadin-button theme="icon" aria-label="Remove" id="removeCombi5">
   <iron-icon icon="lumo:minus"></iron-icon>
  </vaadin-button>
  <vaadin-combo-box id="cbChild5"></vaadin-combo-box>
  <vaadin-combo-box id="cBParent5"></vaadin-combo-box>
 </vaadin-horizontal-layout>
 <vaadin-horizontal-layout class="content" id="vlComboTwin6">
  <vaadin-button theme="icon" aria-label="Remove" id="removeCombi6">
   <iron-icon icon="lumo:minus"></iron-icon>
  </vaadin-button>
  <vaadin-combo-box id="cbChild6"></vaadin-combo-box>
  <vaadin-combo-box id="cBParent6"></vaadin-combo-box>
 </vaadin-horizontal-layout>
 <vaadin-horizontal-layout class="content" id="vlComboTwin7">
  <vaadin-button theme="icon" aria-label="Remove" id="removeCombi7">
   <iron-icon icon="lumo:minus"></iron-icon>
  </vaadin-button>
  <vaadin-combo-box id="cbChild7"></vaadin-combo-box>
  <vaadin-combo-box id="cBParent7"></vaadin-combo-box>
 </vaadin-horizontal-layout>
 <vaadin-horizontal-layout class="content" id="vlComboTwin8">
  <vaadin-button theme="icon" aria-label="Remove" id="removeCombi8">
   <iron-icon icon="lumo:minus"></iron-icon>
  </vaadin-button>
  <vaadin-combo-box id="cbChild8"></vaadin-combo-box>
  <vaadin-combo-box id="cBParent8"></vaadin-combo-box>
 </vaadin-horizontal-layout>
 <vaadin-horizontal-layout class="content" id="vlComboTwin9">
  <vaadin-button theme="icon" aria-label="Remove" id="removeCombi9">
   <iron-icon icon="lumo:minus"></iron-icon>
  </vaadin-button>
  <vaadin-combo-box id="cbChild9"></vaadin-combo-box>
  <vaadin-combo-box id="cBParent9"></vaadin-combo-box>
 </vaadin-horizontal-layout>
 <vaadin-horizontal-layout style="width: 100%; height: 100%;" id="vaadinHorizontalLayout">
  <vaadin-button theme="icon" aria-label="Add new" id="addNewCombo">
   <iron-icon icon="lumo:plus"></iron-icon>
  </vaadin-button>
  <vaadin-button theme="primary" id="acceptPick">
    Aceptar 
  </vaadin-button>
 </vaadin-horizontal-layout>
</vaadin-vertical-layout>
`;
    }

    static get is() {
        return 'combo-twin-for-pick';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(ComboTwinForPick.is, ComboTwinForPick);
