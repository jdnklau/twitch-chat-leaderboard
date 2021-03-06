# Twitch Chat Leaderboard

App to keep track of your most active viewers.

## Installation

Create a `.access-token` file, which only contains your respective access
token. If you do not have an access token yet,
create one [here](https://twitchtokengenerator.com/).
Make sure the token has access to read your chat.

## Usage

```bash
lein run
```

## Todo

* [ ] UI
  * [ ] List leaderboard
  * [ ] Add functionality to reset
  * [ ] Add monthly reminders
  * [ ] Ease creation/definition of access token on initial load-up
* [ ] Switch to SQLite database

## License

Copyright © 2020 Jannik Dunkelau

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
