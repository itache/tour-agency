package me.itache.web.command;

import me.itache.constant.Path;
import me.itache.web.command.auth.*;
import me.itache.web.command.order.SetOrderStatusCommand;
import me.itache.web.command.tour.*;
import me.itache.model.entity.User.Role;
import me.itache.web.command.order.AllOrderListCommand;
import me.itache.web.command.order.CreateOrderCommand;
import me.itache.web.command.order.CustomerOrderListCommand;

import java.util.HashMap;

/**
 * Holds all available application commands
 */
public class CommandRegistry {
    private static HashMap<String, Command> commands = new HashMap<>();

    static {
        commands.put(Path.DEFAULT, new DefaultCommand(Role.all()));
        commands.put(Path.SET_LANG, new SetLanguageCommand(Role.all()));
        commands.put(Path.TOUR_LIST, new TourListCommand(Role.all()));
        commands.put(Path.TOUR_VIEW, new ViewTourCommand(Role.all()));
        commands.put(Path.SEND_CONTACT_MAIL, new SendContactEmailCommand(Role.authenticated()));
        commands.put(Path.ENTER, new EnterCommand(Role.ANONYMOUS));
        commands.put(Path.CONFIRM_REGISTRATION, new ConfirmRegistrationCommand(Role.ANONYMOUS));
        commands.put(Path.TOKEN_RESEND, new ResendConfirmationEmailCommand(Role.ANONYMOUS));
        commands.put(Path.CHANGE_PASSWORD, new ChangePasswordCommand(Role.ANONYMOUS));
        commands.put(Path.RESET_PASSWORD, new ResetPasswordCommand(Role.ANONYMOUS));
        commands.put(Path.ORDER_CUSTOMER_LIST, new CustomerOrderListCommand(Role.CUSTOMER));
        commands.put(Path.ORDER_NEW, new CreateOrderCommand(Role.CUSTOMER));
        commands.put(Path.USER_BLOCKED_PAGE, new BlockedUserPageCommand(Role.MANAGER, Role.CUSTOMER));
        commands.put(Path.LOGOUT, new LogoutCommand(Role.authenticated()));
        commands.put(Path.TOUR_SET_HOT, new SetTourHotCommand(Role.MANAGER, Role.ADMIN));
        commands.put(Path.TOUR_SET_REGULAR, new SetTourRegularCommand(Role.MANAGER, Role.ADMIN));
        commands.put(Path.ORDER_SET_STATUS, new SetOrderStatusCommand(Role.MANAGER, Role.ADMIN));
        commands.put(Path.ORDER_LIST, new AllOrderListCommand(Role.MANAGER, Role.ADMIN));
        commands.put(Path.TOUR_EDIT, new UpdateTourCommand(Role.MANAGER, Role.ADMIN));
        commands.put(Path.TOUR_NEW, new CreateTourCommand(Role.ADMIN));
        commands.put(Path.TOUR_DELETE, new DeleteTourCommand(Role.ADMIN));
        commands.put(Path.TOUR_UPDATE_IMAGE, new UpdateTourImagePageCommand(Role.ADMIN));
        commands.put(Path.TOUR_UPLOAD_IMAGE, new UploadTourImageCommand(Role.ADMIN));
        commands.put(Path.USER_LIST, new UserListCommand(Role.ADMIN));
        commands.put(Path.USER_BLOCK, new BlockUserCommand(Role.ADMIN));
        commands.put(Path.USER_UNBLOCK, new UnblockUserCommand(Role.ADMIN));
    }

    public static Command getCommand(String name) {
        return commands.get(name);
    }
}
