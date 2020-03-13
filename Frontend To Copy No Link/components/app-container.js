import { PolymerElement } from '@polymer/polymer/polymer-element.js';
import { html } from '@polymer/polymer/lib/utils/html-tag.js';
/**
 * An Element for wrapping the app. It is necessary to avoid iOS navbar covering the app
 * when scrolling or switching orientation
 */
class AppContainer extends PolymerElement {
  static get template() {
    return html`
   <style>
      :host {
        display: flex;

        position: absolute;
        top: 0;
        bottom: var(--vaadin-overlay-viewport-bottom);
        left: 0;
        right: 0;
        /* stylelint-disable */
        --vaadin-overlay-viewport-bottom: 0px;
        /* stylelint-enable */
      }
    </style> 
   <slot></slot> 
`;
  }

  static get is() {
    return 'app-container';
  }

  _isIos() {
    return /iPad|iPhone|iPod/.test(navigator.userAgent);
  }

  ready() {
    super.ready();
    if (this._isIos()) {
      window.addEventListener('resize', () => this._detectIosNavbar());
      // prevents scrolling the content
      document.body.style.height = '0.1px';
    }
  }

  connectedCallback() {
    super.connectedCallback();
    if (this._isIos()) {
      this._detectIosNavbar();
    }
  }

  // Borrowed from Vaadin.OverlayElement
  _detectIosNavbar() {
    const innerHeight = window.innerHeight;
    const landscape = window.innerWidth > window.innerHeight;
    const clientHeight = document.documentElement.clientHeight;
    if (landscape && clientHeight > innerHeight) {
      this.style.setProperty('--vaadin-overlay-viewport-bottom', clientHeight - innerHeight + 'px');
    } else {
      this.style.setProperty('--vaadin-overlay-viewport-bottom', '0px');
    }
  }
}

window.customElements.define(AppContainer.is, AppContainer);

