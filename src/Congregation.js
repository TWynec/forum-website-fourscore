import React from 'react';

'use strict';
const e = React.createElement;

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

const domContainer = document.querySelector('#congregation');
const root = ReactDOM.createRoot(domContainer);
root.render(e(Congregation));