import React from 'react';

'use strict';

const e = React.createElement;

class LikeButton extends React.Component {
  constructor(props) {
    super(props);
    this.state = { liked: false };
  }

  render() {
    if (this.state.liked) {
      return 'You liked this.';
    }

    return e(
      'button',
      { onClick: () => this.setState({ liked: true }) },
      'Like'
    );
  }
}

const domContainer = document.querySelector('#like_button_container');
const root = ReactDOM.createRoot(domContainer);
root.render(e(LikeButton));

/*
function Congregation() {
    return (
        <div class="congregation">
            <h2>Congregation</h2>
            <div class="post">
                <table>
                    <td>
                        <p class="post-author">Example Author</p>
                        <p class="post-content">Example post</p>
                    </td>
                </table>
            </div>
        </div>
    );
}
*/
