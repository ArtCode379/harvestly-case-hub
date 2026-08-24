You are running the implementation phase for one Openclaw Android app.

Use these orchestrator instructions as binding context: /home/codex-agent/codex-app-agent/AGENTS.md
Use this screen spec: /home/codex-agent/codex-app-agent/screens-shop.md
Project directory: /tmp/harvestly-case-hub

Task metadata:
- Asana task gid: 1217482143275991
- Asana task name: GB GW4 C1527
- Asana URL: https://app.asana.com/1/1208304498069546/project/1213586227413017/task/1217482143275991
- App name: Harvestly Case Hub
- Company: HARVESTLY LTD
- Domain: http://harvestly.casa/
- Package: harvestly.accessories.harvestlycasehub
- Prefix: DOFIP
- Type: shop
- Description: Специфика компании - торговля чехлами для телефонов и мобильными аксессуарами. Приложение по продаже товаров компании, содержит список всех товаров компании (если разных категорий - с возможностью сортировки по категориям). История покупок. Корзина товаров с формой бронирования заказа. После подтверждения бронирования пользователь видит баннер с информацией о номере и деталях заказа с уведомлением о том, что его ожидают в магазине в течении 24 часов. Настройки должны содержать информацию о: названии компании, версии приложения, линку Customers Support со ссылкой сайт компании.
(можно разнообразить главную страницу каруселью с заметками или статьями о защите смартфонов и полезных мобильных аксессуарах)

Do Phase 2 and Phase 3 only:
1. Extract or derive the style guide.
2. Do not create project-local agent instruction files inside /tmp/harvestly-case-hub.
3. Implement all required screens/content/data/assets/icon according to the orchestrator AGENTS.md and the screen spec.
4. Icon generation is best-effort: if Leonardo/imagegen cannot provide a filesystem-backed icon quickly, continue implementing the app with existing assets.
5. Do not push to GitHub, do not update Asana, and do not send Slack.
6. You may run local checks while implementing, but the runner will run quality/build afterward.
7. Keep every Kotlin file conventionally formatted: one statement per line, annotations above declarations, expanded indented Compose blocks, no semicolon-compressed code, and no source line longer than 200 characters.
