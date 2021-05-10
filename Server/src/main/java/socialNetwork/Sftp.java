package socialNetwork;

import com.jcraft.jsch.*;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;

import java.io.IOException;

public class Sftp {
    private String remoteHost = "192.168.43.211";
    private String username = "tester";
    private String password = "password";

    private ChannelSftp setupJsch() throws JSchException {
        JSch jsch = new JSch();
        jsch.setKnownHosts("C:\\Users\\Virgil\\.ssh\\known_hosts");
        Session jschSession = jsch.getSession(username, remoteHost);
        jschSession.setPassword(password);
        jschSession.connect(1000000000);
        return (ChannelSftp) jschSession.openChannel("sftp");
    }

    public void uploadFile(String filename) throws JSchException, SftpException {
        ChannelSftp channelSftp = setupJsch();
        channelSftp.connect();

        String localFileImg = "images/"+filename+".png";
        String localFileHtml = "images/"+filename+".html";
        String remoteDir = "C:\\Users\\Virgil\\Desktop\\RebexTinySftpServer-Binaries-Latest\\data";

        channelSftp.put(localFileImg, remoteDir + filename + ".png");
        channelSftp.put(localFileHtml, remoteDir + filename + ".html");

        channelSftp.exit();
    }

    private SSHClient setupSshj() throws IOException {
        SSHClient client = new SSHClient();
        client.addHostKeyVerifier(new PromiscuousVerifier());
        client.connect(remoteHost);
        client.authPassword(username, password);
        return client;
    }

    public void uploadFiles(String filename) throws IOException {
        SSHClient sshClient = setupSshj();
        SFTPClient sftpClient = sshClient.newSFTPClient();

        String localFileImg = "images/"+filename+".png";
        String localFileHtml = "images/"+filename+".html";
        String remoteDir = "/";

        sftpClient.put(localFileImg, remoteDir + filename + ".png");
        sftpClient.put(localFileHtml, remoteDir + filename + ".html");
        System.out.println("fisiere trimise cu suces!");

        sftpClient.close();
        sshClient.disconnect();
    }

//    public void download() throws JSchException, SftpException {
//        ChannelSftp channelSftp = setupJsch();
//        channelSftp.connect();
//
//        String remoteFile = "testfile.txt";
//        String localDir = "images/";
//
//        channelSftp.get(remoteFile, localDir + "jschFile.txt");
//
//        channelSftp.exit();
//    }

    public void download() throws IOException {
        SSHClient sshClient = setupSshj();
        SFTPClient sftpClient = sshClient.newSFTPClient();

        String remoteFile = "testfile.txt";
        String localDir = "images/";

        sftpClient.get(remoteFile, localDir + "sshjFile.txt");

        sftpClient.close();
        sshClient.disconnect();
    }
}
