import { PolymerElement } from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';
import '@vaadin/vaadin-dialog/src/vaadin-dialog.js';
import '../../styles/shared-styles.js';
import { html } from '@polymer/polymer/lib/utils/html-tag.js';
{
  class ConfirmationDialogElement extends PolymerElement {
    static get template() {
      return html`
   <vaadin-dialog opened="{{opened}}"> 
    <template> 
     <style>
          #caption {
            font-size: 14px;
            text-align: center;
            color: var(--lumo-body-text-color);
          }

          #message {
            padding: 0 16px;
            color: grey;
          }

          #buttons {
            margin-top: 30px;
            display: flex;
            box-shadow: 0 -3px 3px -3px var(--lumo-shade-20pct);
          }
        </style> 
     <div> 
      <p id="caption">[[caption]]</p> 
      <p id="message">[[message]]</p> 
      <div id="buttons"> 
       <vaadin-button on-click="_ok" theme="raised tertiary error">
        [[okText]]
       </vaadin-button> 
       <div style="flex: 1"></div> 
       <vaadin-button on-click="_cancel" theme="raised tertiary" class="right">
        [[cancelText]]
       </vaadin-button> 
      </div> 
     </div> 
    </template> 
   </vaadin-dialog> 
`;
    }

    static get is() {
      return 'confirm-dialog';
    }

    static get properties() {
      return {
        caption: {
          value: 'Confirmation needed'
        },
        message: {
          value: 'Do you want to continue?'
        },
        okText: {
          value: 'OK'
        },
        cancelText: {
          value: 'Cancel'
        },
        opened: {
          type: Boolean,
          notify: true,
          value: false,
          observer: '_openedChanged'
        },
        ok: Boolean
      };
    }

    _cancel() {
      this.opened = false;
    }

    _ok() {
      this.ok = true;
      this.opened = false;
    }

    _openedChanged(opened) {
      if (opened) {
        this.ok = false;
        return;
      }

      if (this.ok) {
        this.dispatchEvent(new CustomEvent('ok-click'));
      } else {
        this.dispatchEvent(new CustomEvent('cancel-click'));
      }
    }
  }

  window.customElements.define(ConfirmationDialogElement.is, ConfirmationDialogElement);
}

